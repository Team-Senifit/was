-- 데이터 마이그레이션: programs_bundles, bundles_videos → programs_videos
-- Video ID 11번 제외하고 program_id 1001-2003까지만 마이그레이션 진행

start transaction;
-- 1. 기존 데이터 확인 (video_id 11 제외, program_id 1001-2003 범위)
SELECT
    'programs_bundles count (range 1001-2003)' as table_name,
    COUNT(*) as count
FROM programs_bundles
WHERE program_id BETWEEN 1001 AND 2003
UNION ALL
SELECT 'bundles_videos count (excluding video 11, range 1001-2003)', COUNT(*)
FROM bundles_videos bv
JOIN programs_bundles pb ON bv.bundle_id = pb.bundle_id
WHERE bv.video_id != 11 AND pb.program_id BETWEEN 1001 AND 2003
UNION ALL
SELECT 'bundles count (used in range 1001-2003)', COUNT(DISTINCT pb.bundle_id)
FROM programs_bundles pb
WHERE pb.program_id BETWEEN 1001 AND 2003;

-- 2. 마이그레이션 실행 (video_id 11 제외)
INSERT IGNORE INTO programs_videos (program_id, video_id, sequence)
SELECT
    ranked.program_id,
    ranked.video_id,
    ROW_NUMBER() OVER (
        PARTITION BY ranked.program_id
        ORDER BY ranked.bundle_sequence ASC, ranked.video_sequence ASC
    ) as sequence
FROM (
    SELECT DISTINCT
        pb.program_id,
        bv.video_id,
        pb.sequence as bundle_sequence,
        bv.sequence as video_sequence
    FROM programs_bundles pb
    JOIN bundles_videos bv ON pb.bundle_id = bv.bundle_id
    WHERE bv.video_id != 11  -- 🔴 video_id 11 제외
    ORDER BY pb.program_id, pb.sequence, bv.sequence
) ranked;

-- 3. 마이그레이션 결과 확인 (program_id 1001-2003 범위)
SELECT
    'programs_videos after migration (range 1001-2003, excluding video 11)' as info,
    COUNT(*) as count
FROM programs_videos
WHERE program_id BETWEEN 1001 AND 2003;

-- 4. video_id 11이 포함되지 않았는지 검증
SELECT
    'Video 11 in programs_videos' as check_type,
    COUNT(*) as count
FROM programs_videos
WHERE video_id = 11;

-- 5. 데이터 무결성 검증 (program_id 1001-2003 범위)
SELECT
    pv.program_id,
    COUNT(DISTINCT pv.video_id) as unique_videos,
    COUNT(*) as total_entries,
    MAX(pv.sequence) as max_sequence
FROM programs_videos pv
WHERE pv.program_id BETWEEN 1001 AND 2003
GROUP BY pv.program_id
ORDER BY pv.program_id;

-- 6. 마이그레이션 상세 결과 확인 (program_id 1001-2003 범위)
-- 주의: programs, videos 테이블이 없는 경우 이 쿼리는 스킵됨
-- 실제 운영 DB에서는 다음 쿼리를 사용:
-- SELECT
--     p.id as program_id,
--     p.name as program_name,
--     COUNT(pv.video_id) as video_count,
--     GROUP_CONCAT(v.name ORDER BY pv.sequence SEPARATOR ', ') as videos
-- FROM programs p
-- JOIN programs_videos pv ON p.id = pv.program_id
-- JOIN videos v ON pv.video_id = v.id
-- WHERE v.id != 11
-- AND p.id BETWEEN 1001 AND 2003
-- GROUP BY p.id, p.name
-- ORDER BY p.id;

-- 대신 programs_videos 테이블만 확인
SELECT
    pv.program_id,
    COUNT(pv.video_id) as video_count,
    GROUP_CONCAT(pv.video_id ORDER BY pv.sequence SEPARATOR ', ') as video_ids,
    MAX(pv.sequence) as max_sequence
FROM programs_videos pv
WHERE pv.program_id BETWEEN 1001 AND 2003
AND pv.video_id != 11
GROUP BY pv.program_id
ORDER BY pv.program_id;

commit;