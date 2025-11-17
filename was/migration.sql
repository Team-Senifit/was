-- 마이그레이션 SQL: 기존 bundle 구조 → programs_videos 구조

-- 1. 기존 데이터 확인
SELECT 'programs_bundles count:' as info, COUNT(*) as count FROM programs_bundles
UNION ALL
SELECT 'bundles count:', COUNT(*) FROM bundles  
UNION ALL
SELECT 'bundle_videos count:', COUNT(*) FROM bundle_videos;

-- 2. programs_videos 테이블 생성 (이미 존재한다면 생략)
-- CREATE TABLE programs_videos (
--     program_id BIGINT NOT NULL,
--     video_id BIGINT NOT NULL, 
--     sequence INT NOT NULL,
--     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
--     updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--     PRIMARY KEY (program_id, video_id),
--     FOREIGN KEY (program_id) REFERENCES programs(id),
--     FOREIGN KEY (video_id) REFERENCES videos(id)
-- );

-- 3. 마이그레이션 쿼리: 기존 구조에서 새로운 구조로 데이터 변환

-- 방법 1: ROW_NUMBER() 사용 (MySQL 8.0+)
INSERT INTO programs_videos (program_id, video_id, sequence)
SELECT
    ranked.program_id,
    ranked.video_id,
    ranked.row_num as sequence
FROM (
    SELECT
        pb.program_id,
        bv.video_id,
        ROW_NUMBER() OVER (
            PARTITION BY pb.program_id
            ORDER BY pb.sequence ASC, bv.sequence ASC
        ) as row_num
    FROM programs_bundles pb
    JOIN bundle_videos bv ON pb.bundle_id = bv.bundle_id
) ranked;

-- 방법 2: 변수 사용 (MySQL 5.7 호환)
-- SET @row_num = 0;
-- SET @current_program = 0;
-- INSERT INTO programs_videos (program_id, video_id, sequence)
-- SELECT
--     program_id,
--     video_id,
--     CASE
--         WHEN @current_program != program_id THEN @row_num := 1
--         ELSE @row_num := @row_num + 1
--     END as sequence,
--     @current_program := program_id
-- FROM (
--     SELECT
--         pb.program_id,
--         bv.video_id
--     FROM programs_bundles pb
--     JOIN bundle_videos bv ON pb.bundle_id = bv.bundle_id
--     ORDER BY pb.program_id, pb.sequence, bv.sequence
-- ) ordered_videos;

-- 4. 마이그레이션 결과 확인
SELECT 'programs_videos migrated count:' as info, COUNT(*) as count FROM programs_videos;

-- 5. 데이터 무결성 검증
SELECT 
    pv.program_id,
    COUNT(*) as video_count,
    MAX(pv.sequence) as max_sequence,
    MIN(pv.sequence) as min_sequence
FROM programs_videos pv
GROUP BY pv.program_id
ORDER BY pv.program_id;

-- 6. 기존 테이블 백업 (선택사항 - 실행 전 반드시 확인)
-- CREATE TABLE programs_bundles_backup AS SELECT * FROM programs_bundles;
-- CREATE TABLE bundle_videos_backup AS SELECT * FROM bundle_videos;
-- CREATE TABLE bundles_backup AS SELECT * FROM bundles;

-- 7. 마이그레이션 검증 쿼리들
-- 특정 프로그램의 비디오 순서 확인
SELECT
    pv.program_id,
    p.name as program_name,
    pv.sequence,
    v.name as video_name,
    v.duration
FROM programs_videos pv
JOIN programs p ON pv.program_id = p.id
JOIN videos v ON pv.video_id = v.id
WHERE pv.program_id = 1  -- 특정 프로그램 ID로 변경
ORDER BY pv.sequence;

-- 8. 롤백 시나리오 (필요시)
-- DELETE FROM programs_videos;
-- INSERT INTO programs_bundles SELECT * FROM programs_bundles_backup;
-- INSERT INTO bundle_videos SELECT * FROM bundle_videos_backup;
-- INSERT INTO bundles SELECT * FROM bundles_backup;


