#!/bin/bash

# 기능별 커밋 스크립트

echo '=== 1. 프로그램-비디오 관계 엔티티 및 레포지토리 ==='
git add src/main/java/com/senifit/was/entity/ProgramVideo.java
git add src/main/java/com/senifit/was/entity/ProgramVideoId.java
git add src/main/java/com/senifit/was/repository/program/api/ProgramVideoRepository.java
git commit -m 'feat: 프로그램-비디오 다대다 관계 엔티티 추가

- ProgramVideo 엔티티 및 ProgramVideoId 복합키 클래스 추가
- ProgramVideoRepository 인터페이스 추가
- programs_videos 테이블 매핑을 위한 JPA 설정'

echo '=== 2. TSID ID 생성기 ==='
git add src/main/java/com/senifit/was/util/TsidIdGenerator.java
git commit -m 'feat: TSID 기반 ID 생성기 추가

- TsidIdGenerator 유틸리티 클래스 추가
- Program 엔티티에서 TSID 사용을 위한 기반 코드'

echo '=== 3. 비디오 데이터 캐시 시스템 ==='
git add src/main/java/com/senifit/was/repository/video_data/api/VideoDataRepository.java
git add src/main/java/com/senifit/was/repository/video_data/impl/VideoDataCacheRepository.java
git add src/main/java/com/senifit/was/bootstrap/cache/VideoDataCacheInitializer.java
git add src/main/java/com/senifit/was/bootstrap/cache/VideoDataCacheInitializerRunner.java
git add src/main/java/com/senifit/was/vo/VideoCacheKey.java
git add src/main/java/com/senifit/was/vo/VideoData.java
git commit -m 'feat: 비디오 데이터 캐시 시스템 구현

- VideoDataRepository 인터페이스 및 캐시 구현체 추가
- VideoDataCacheInitializer를 통한 애플리케이션 시작 시 캐시 초기화
- VideoCacheKey 및 VideoData VO 클래스 추가'

echo '=== 4. 추천 시스템 ==='
git add src/main/java/com/senifit/was/service/recommendation/RecommendationService.java
git add src/main/java/com/senifit/was/service/recommendation/ByPersonalRecommendationHelper.java
git add src/main/java/com/senifit/was/service/recommendation/by_personal_engines/BaseRecommendationEngine.java
git add src/main/java/com/senifit/was/service/recommendation/by_personal_engines/v1/AssembleHelper.java
git add src/main/java/com/senifit/was/service/recommendation/by_personal_engines/v1/BasicRecommendationEngineV1.java
git add src/main/java/com/senifit/was/service/recommendation/by_personal_engines/v1/MetaDataHelper.java
git add src/main/java/com/senifit/was/vo/ProgramData.java
git add src/main/java/com/senifit/was/service/VideoService.java
git commit -m 'feat: 개인 맞춤 추천 시스템 구현

- RecommendationService 및 헬퍼 클래스 추가
- BasicRecommendationEngineV1 추천 엔진 구현
- ProgramData VO 및 VideoService 추가'

echo '=== 5. 마이그레이션 SQL ==='
git add test_api/migration.sql
git commit -m 'feat: 데이터베이스 마이그레이션 SQL 추가

- programs_bundles → programs_videos 테이블 데이터 마이그레이션
- program_id 범위 제한 (1001-2003) 및 video_id 11 제외
- INSERT IGNORE를 통한 중복 데이터 처리'

echo '=== 6. 기타 리팩토링 및 수정 ==='
git add .
git commit -m 'refactor: 서비스 및 컨트롤러 리팩토링

- ProgramService로 WorkoutDataDtoService 리네임
- 추천 컨트롤러 및 DTO 수정
- PersonalProgramRepository 추가
- 기타 설정 파일 및 테스트 수정'

echo '모든 커밋이 완료되었습니다!'

