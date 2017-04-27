# txt2img

This is a simple routine for turning an input text into image data, similar to a captcha but not so smart. In addition, it captures the routine as a deployable Amazon Web Service Lambda function.

## Quick start

Build all artifacts for this project:

`gradle cleanAll all`

Artifacts (5 total)

1. `lib/lambda-deploy.zip` The deployable Lambda artifact
2. `lib/txt2img-<version>-*` Compiled and source artifacts (4)

## Lambda

The Lambda function can be configured with an environment variable `textLimit` that will not do any conversion if the input text is greater than the configured value. If not set, then the default value is 50 characters long.

Example input to Lambda function:

```
{
  "text": "1234567890",
  "font": "Arial",
  "size": 20
}
```