#!/bin/bash

# Migration SQL 파일 변경사항 커밋 스크립트
echo 'Adding migration.sql file...'
git add test_api/migration.sql

echo 'Committing changes...'
git commit -m 'feat: 마이그레이션 SQL 업데이트 - program_id 범위 제한 및 INSERT IGNORE 적용

- program_id 범위를 1001에서 2003으로 제한
- video_id 11 제외 조건 추가  
- INSERT IGNORE로 중복 데이터 처리
- ROW_NUMBER 구문 수정 (콤마 제거)'

echo 'Commit completed successfully!'

