#!/bin/bash

./gradlew build -x test

docker-compose up -d --build --force-recreate java
docker-compose up -d --no-recreate db
