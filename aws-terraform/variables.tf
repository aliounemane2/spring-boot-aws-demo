variable "name" {
  description = "The application name"
}

variable "region" {
  description = "AWS region"
  default = "us-east-1"
}

variable "environment" {
  description = "The application environment"
  default = "test"
}

variable "cidr" {
  description = "The CIDR block for the VPC."
  default = "10.0.0.0/16"
}

variable "public_subnets" {
  description = "List of public subnets"
  default = ["10.0.16.0/20", "10.0.48.0/20"]
}

/*variable "private_subnets" {
  description = "List of private subnets"
}*/

variable "availability_zones" {
  description = "List of availability zones"
  default = ["us-east-1a", "us-east-1b"]
}

variable "s3_bucket_name" {
  description = "S3 bucket name"
}

variable "container_image" {
  description = "Docker image"
}

variable "container_port" {
  description = "The port where the Docker is exposed"
  default     = 8080
}

variable "container_cpu" {
  description = "The number of cpu units used by the task"
  default     = 512
}

variable "container_memory" {
  description = "The amount (in MiB) of memory used by the task"
  default     = 1024
}

variable "health_check_path" {
  description = "Http path for task health check"
  default     = "/"
}

variable "desired_task_count" {
  description = "Number of ECS tasks to run"
  default     = 1
}
