#!/bin/bash

echo "docker compose down"
docker compose down

ALL_CONTAINERS=$(docker ps -a)
echo "01-01 stop all containers"
docker stop "$ALL_CONTAINERS"

echo "01-02 remove all containers"
docker container rm -f -v "$ALL_CONTAINERS"

echo "02. remove all images"
ALL_IMAGES=$(docker images)
docker rmi -f "$ALL_IMAGES"

echo "03. remove all volumes"
ALL_VOLUMES=$(docker volume ls)
docker volume rm "$ALL_VOLUMES"

#remove all unused containers, networks, images, and volumes
echo "04. remove all cache"
docker system prune -a