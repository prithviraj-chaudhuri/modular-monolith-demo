.PHONY: build up down

build:
	podman-compose --env-file .env build

up:
	podman-compose --env-file .env up

down:
	podman-compose --env-file .env down
