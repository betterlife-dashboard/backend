SERVICES = auth todo gateway

all: docker-build up

build:
	@for s in $(SERVICES); do \
		echo " Building $$s..."; \
		cd $$s && ./gradlew clean build && cd ..; \
	done

docker-build:
	docker compose build --no-cache

up:
	docker compose up -d

down:
	docker compose down

reset:
	docker compose down -v
	docker system prune -af

