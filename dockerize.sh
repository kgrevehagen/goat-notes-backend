#!/bin/sh

./gradlew clean bootJar

docker compose up --build