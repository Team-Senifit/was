#!/usr/bin/env bash
set -euo pipefail

# Usage: run inside /apps/senifit (docker-compose.yml 위치)

infra_down=0

check_service_running() {
  local service="$1"
  local cid
  cid="$(docker compose ps -q "$service" || true)"
  if [[ -z "$cid" ]]; then
    return 1
  fi
  local status
  status="$(docker inspect -f '{{.State.Status}}' "$cid" 2>/dev/null || true)"
  [[ "$status" == "running" ]]
}

if ! check_service_running mysql; then
  infra_down=1
fi

if ! check_service_running redis; then
  infra_down=1
fi

if [[ "$infra_down" -eq 1 ]]; then
  echo "[INFO] mysql/redis 중 하나 이상 down -> 전체 재시작"
  docker compose down
  docker compose up -d --build
else
  echo "[INFO] mysql/redis 정상 -> 기존 방식 (spring-boot만 갱신)"
  docker compose down spring-boot
  docker compose pull spring-boot
  docker compose up -d spring-boot
fi
