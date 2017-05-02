# txt2img

This is a simple routine for turning an input text into image data, similar to a captcha but not so smart. In addition, it packages the routine as a deployable Amazon Web Service Lambda function.

## Quick start

Build all artifacts for this project:

`gradle cleanAll all`

Artifacts (5 total)

1. `lib/lambda-deploy.zip` The deployable Lambda artifact
2. `lib/txt2img-<version>-*` Compiled and source artifacts (4)

## Lambda

The Lambda function can be configured with an environment variable `textLimit` that will not do any conversion if the input text is greater than the configured value. If not set, then the default value is 50 characters long.

Lambda configuration
- Code entry type: .zip/jar file
- Runtime: Java 8
- Handler: `txt2img.lambda.ImageHandler::toImage`
- Memory: 128MB
- Timeout: 5s

Example input to Lambda function:

```
{
  "text": "1234567890",
  "font": "Arial",
  "size": 20
}
```

## Lambda Node.js 

A simple Node.js project is included to test the deployed Lambda function. It is assumed that you have `~/.aws/credentials` configured. 

Navigate into `nodejs` and execute:

```
npm install aws-sdk
npm install prompt
node index.js
```
