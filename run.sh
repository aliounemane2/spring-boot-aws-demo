#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_deps=${project_dir}/docker-compose.yml
declare dc_app=${project_dir}/docker-compose-app.yml
declare services="spring-boot-geeks"

function build_api() {
    ./mvnw clean package -DskipTests
}

function build_image() {
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/spring-boot-geeks
}

function start_deps() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_deps}" up --build --force-recreate -d
    docker-compose -f "${dc_deps}" logs -f
}

function start() {
    #build_api
    build_image
    echo "Starting app and dependent docker containers...."
    docker-compose -f "${dc_deps}" -f "${dc_app}" up --build --force-recreate -d ${services}
    docker-compose -f "${dc_deps}" -f "${dc_app}" logs -f ${services}
}

function stop() {
    echo "Stopping dependent docker containers...."
    docker-compose -f "${dc_deps}" -f "${dc_app}" stop
    docker-compose -f "${dc_deps}" -f "${dc_app}" rm -f
}

function restart() {
    stop
    sleep 5
    start
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
