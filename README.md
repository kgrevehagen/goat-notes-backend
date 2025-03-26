A service for creating and listing notes for users.

## Setup

This must be run with certain environment variables set, whether you're running it locally or in the cloud. These are:
- `AWS_REGION`: The AWS region to use for the AWS SDK. Must match the region of DynamoDB instance.
- `AWS_PROFILE`: The AWS profile to use for the AWS SDK.
- `COGNITO_ISSUER_URI`: The issuer URI for the Cognito user pool. This is used to validate JWTs.
- `PORT`: The port you want the server to run on.

For ease of use, these can be set in an `.env` file in the root of the project.
Apply this `.env` file in your run configuration when running locally. For cloud, depending on your provider, set the environment variables in the appropriate way. 
