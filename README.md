# SpringBoot AWS Demo

```shell
$ ./mvnw clean package
$ ./mvnw spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=sivaprasadreddy/spring-boot-aws-demo
$ docker run -d -p 8181:8080 sivaprasadreddy/spring-boot-aws-demo
```

## Using docker-compose

```shell
$ ./run.sh restart
```

## Deploying on AWS
```shell
$ export AWS_ACCOUNT_ID=XXXXXX
$ export AWS_REGION=XXXXXX
$ export ECR_REPO_NAME=spring-boot-aws-demo-test
$ export IMAGE_VERSION=1.0.0
$ aws ecr create-repository --repository-name ${ECR_REPO_NAME} --region ${AWS_REGION}
$ aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}
$ ./mvnw spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_VERSION}
$ docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_VERSION}

## Deploy without ALB
$ cd aws-terraform
$ terraform init
$ terraform validate
$ terraform apply
$ terraform destroy
```
