resource "aws_s3_bucket" "randomjokes_bucket" {
  bucket = var.s3_bucket_name
  force_destroy = true
}

resource "aws_s3_bucket_public_access_block" "randomjokes_bucket_access" {
  bucket = aws_s3_bucket.randomjokes_bucket.id

  block_public_acls   = true
  block_public_policy = true
  ignore_public_acls  = true
}

resource "aws_iam_policy" "s3_bucket_policy" {
  name        = "${var.name}-task-policy-s3"
  description = "Policy that allows access to S3"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
          "Action": [
            "s3:ListAllMyBuckets"
          ],
          "Effect": "Allow",
          "Resource": "*"
        },
        {
            "Effect": "Allow",
            "Action" : [
              "s3:*"
            ],
            "Resource": "${aws_s3_bucket.randomjokes_bucket.arn}"
        }
    ]
}
EOF
}
