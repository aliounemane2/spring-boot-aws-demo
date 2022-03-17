resource "aws_s3_bucket" "main" {
  bucket = "${var.name}-${var.s3_bucket_name}-${var.environment}"
  force_destroy = true
}

resource "aws_s3_bucket_public_access_block" "main_bucket_access" {
  bucket = aws_s3_bucket.main.id

  block_public_acls   = true
  block_public_policy = true
  ignore_public_acls  = true
}
