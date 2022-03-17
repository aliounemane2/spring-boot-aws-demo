
resource "aws_cloudwatch_log_group" "main" {
  name = "/ecs/${var.name}-task-${var.environment}"
  tags = {
    Name        = "${var.name}-task-${var.environment}"
    Environment = var.environment
  }
}
