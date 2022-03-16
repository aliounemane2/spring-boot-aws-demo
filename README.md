# SpringBoot Geeks

```shell
$ ./mvnw clean package
$ ./mvnw spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=sivaprasadreddy/spring-boot-geeks
$ docker run -d -p 8181:8080 sivaprasadreddy/spring-boot-geeks
```

## Using docker-compose

```shell
$ ./run.sh restart
```

## Deploying on AWS
```shell
$ aws ecr create-repository --repository-name spring-boot-geeks --region ${AWS_REGION}
$ aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-boot-geeks
$ ./mvnw spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-boot-geeks
$ docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/spring-boot-geeks

## Deploy without ALB
$ cd aws-terraform
$ terraform init
$ terraform validate
$ terraform apply
$ terraform destroy
```
