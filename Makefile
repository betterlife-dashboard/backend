
# ------------------------------
# COMMON COMMANDS
# ------------------------------

help:
	@echo "🔧 Available commands:"
	@echo " make up            - 개발용 MSA 전체 실행"
	@echo " make up-db         - DB/Redis만 실행"
	@echo " make up-auth       - Auth 서비스만 재실행"
	@echo " make up-todo       - Todo 서비스만 재실행"
	@echo " make up-gateway    - Gateway 서비스만 재실행"
	@echo " make down          - 전체 컨테이너 종료"
	@echo " make logs-auth     - Auth 로그 보기"
	@echo " make logs-todo     - Todo 로그 보기"
	@echo " make logs-gateway  - Gateway 로그 보기"
	@echo " make clean         - 사용하지 않는 이미지/컨테이너 정리"
	@echo ""
	@echo "🔥 [운영] Prod Commands"
	@echo " make prod-up       - 운영용 전체 실행"
	@echo " make prod-auth     - 운영에서 Auth만 재배포"
	@echo " make prod-todo     - 운영에서 Todo만 재배포"
	@echo " make prod-gateway  - 운영에서 Gateway만 재배포"
	@echo " make prod-down     - 운영 전체 종료"

# ------------------------------
# LOCAL DEVELOPMENT COMMANDS
# ------------------------------

# 개발용 전체 MSA 실행
up:
	docker compose  up -d

up-build:
	docker compose  up -d --build

# DB/Redis만 실행
up-db:
	docker compose  up -d mysql-auth mysql-todo redis

# 특정 서비스만 재빌드 + 재시작
up-auth:
	docker compose  up -d --build auth

up-todo:
	docker compose  up -d --build todo

up-gateway:
	docker compose  up -d --build gateway

# 종료
down:
	docker compose  down

# ------------------------------
# LOGS
# ------------------------------

logs-auth:
	docker compose  logs -f auth

logs-todo:
	docker compose  logs -f todo

logs-gateway:
	docker compose  logs -f gateway

# ------------------------------
# CLEANUP
# ------------------------------

clean:
	docker system prune -f

# ------------------------------
# PRODUCTION COMMANDS (EC2)
# ------------------------------

prod-up:
	docker compose -f docker-compose.prod.yml up -d

prod-auth:
	docker compose -f docker-compose.prod.yml up -d auth

prod-todo:
	docker compose -f docker-compose.prod.yml up -d todo

prod-gateway:
	docker compose -f docker-compose.prod.yml up -d gateway

prod-down:
	docker compose -f docker-compose.prod.yml down

.PHONY: help up up-db up-auth up-todo up-gateway down logs-auth logs-todo logs-gateway clean prod-up prod-auth prod-todo prod-gateway prod-down
