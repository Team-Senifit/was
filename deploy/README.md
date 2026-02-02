# Deploy (GHCR + AWS SSM)

`main` 브랜치 push 시 GitHub Actions가 다음을 수행합니다.

1. **Docker 빌드** → **ghcr.io 푸시** (태그: `commit SHA` + `latest`)
2. **AWS SSM**으로 대상 EC2에 명령 전송 → `/apps/senifit`에서 `SPRING_BOOT_IMAGE=ghcr.io/...:<sha>` export 후 `docker compose pull` & `up -d`

실제 사용 중인 compose: [docker-compose.yml](./docker-compose.yml)  
- **spring-boot** 서비스: `image: ${SPRING_BOOT_IMAGE}` → CI가 배포 시 `SPRING_BOOT_IMAGE=ghcr.io/<repo>:<commit-sha>` 로 설정
- **admin** 서비스: `image: ${ADMIN_BOOT_IMAGE}` → CI에서는 건드리지 않음 (서버 .env 값 유지)

## GitHub Secrets

| Secret | 설명 |
|--------|------|
| `AWS_ACCESS_KEY_ID` | SSM SendCommand 권한이 있는 IAM 키 |
| `AWS_SECRET_ACCESS_KEY` | 위 IAM 시크릿 |
| `AWS_REGION` | (선택) 리전. 없으면 `ap-northeast-2` |
| `SSM_INSTANCE_ID` | 배포 대상 EC2 인스턴스 ID (예: `i-0abc123...`) |

## EC2(/apps/senifit) 준비

1. **SSM Agent**  
   EC2에 SSM Agent 설치·실행, 해당 인스턴스에 대한 IAM 역할(또는 사용 중인 키)에 `ssm:SendCommand` 등 필요 권한 부여.

2. **Docker & Docker Compose**  
   서버에 Docker, Docker Compose 설치.

3. **docker-compose.yml**  
   `/apps/senifit`에는 이 디렉터리의 [docker-compose.yml](./docker-compose.yml)과 동일한 구성을 두고, 서버용 `.env`에 `SPRING_BOOT_IMAGE`, `ADMIN_BOOT_IMAGE` 등 필요한 변수를 설정.  
   CI 배포 시 **SPRING_BOOT_IMAGE**만 현재 커밋 SHA 이미지로 덮어서 `docker compose up -d` 합니다.

4. **SSL keystore (spring-boot)**  
   `spring-boot` 컨테이너는 `/app/cert`에 keystore를 마운트하도록 구성되어 있습니다.  
   서버에 `/apps/senifit/cert/keystore.p12`를 두고, `.env`에 아래 값을 설정하세요.

   ```
   SSL_ENABLED=true
   SSL_KEYSTORE_PATH=/app/cert/keystore.p12
   SSL_KEYSTORE_PASSWORD=...
   ```

5. **ghcr.io 비공개 이미지인 경우**  
   EC2에서 한 번만 로그인:

   ```bash
   echo $GITHUB_PAT | docker login ghcr.io -u YOUR_GITHUB_USER --password-stdin
   ```

   (패키지 읽기 권한이 있는 PAT를 변수/시크릿으로 관리)

## 이미지 주소 (spring-boot)

- `ghcr.io/<owner>/<repo>:<commit-sha>`  
- `ghcr.io/<owner>/<repo>:latest`  

`<owner>/<repo>`는 해당 GitHub 저장소 이름(소문자)과 동일합니다.
