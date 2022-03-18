#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_deps=${project_dir}/docker-compose.yml
declare dc_app=${project_dir}/docker-compose-app.yml
declare services="spring-boot-aws-demo"

function build_api() {
    ./mvnw clean package -DskipTests
}

function build_image() {
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/spring-boot-aws-demo
}

function build_image_jib() {
    ./mvnw clean package -DskipTests jib:build -Dimage=sivaprasadreddy/spring-boot-aws-demo
}

function start_deps() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_deps}" up --build --force-recreate -d
    docker-compose -f "${dc_deps}" logs -f
}

function start() {
    #build_api
    build_image
    #build_image_jib
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

function awsEcrPush() {
  export AWS_ACCOUNT_ID="972824157910"
  export AWS_REGION="us-east-1"
  export AWS_PROFILE="siva"
  export ECR_REPO_NAME="spring-boot-aws-demo-test"
  export IMAGE_VERSION="1.0.0"
  ./mvnw spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_VERSION}
  #./mvnw clean package -DskipTests jib:build -Dimage=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_VERSION}
  #aws ecr create-repository --repository-name ${ECR_REPO_NAME} --region ${AWS_REGION}
  aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}
  docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_VERSION}
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
