A service for creating and listing notes for users.

## Setup

This must be run with certain environment variables set, whether you're running it locally or in the cloud. These are:
- `AWS_REGION`: The AWS region to use for the AWS SDK. Must match the region of DynamoDB instance. Defaults to `eu-north-1`.
- `AWS_PROFILE`: The AWS profile to use for the AWS SDK.
- `COGNITO_ISSUER_URI`: The issuer URI for the Cognito user pool. This is used to validate JWTs. E.g. `https://cognito-idp.eu-north-1.amazonaws.com/<user_pool_id>`.
- `PORT`: The port you want the server to run on. Defaults to 8080.

For ease of use, these can be set in an `.env` file in the root of the project.
Apply this `.env` file in your run configuration when running locally. For cloud, depending on your provider, set the environment variables in the appropriate way. 

### AWS CLI for communicating with aws from the server
- Install the AWS CLI, e.g. via homebrew.
- Enable AWS IAM Identity Center (formerly SSO) in your AWS account.
- Run `aws configure sso` and follow instructions. This is where you set your profile name.
- For logging in: `aws sso login --profile <your profile name>`
- For verifying login: `aws sts get-caller-identity --profile <your profile name>`

### AWS Cognito setup
This service uses AWS Cognito for user authentication. You need to set up a Cognito User Pool and an App Client with authorization code grant with PKCE in the AWS console.

### Postman OAuth 2.0 setup
In order to test this service via Postman, you need to have a token. Here's a setup guide for achieving that in Postman, with automatic refreshing.

Select your collection in Postman and go to the Authorization tab.

- Auth Type: OAuth 2.0
- Add auth data to: Request Headers
- Header Prefix: Bearer

**Configure New Token**
- Token Name: `<whatever you want>`
- Grant Type: `Authorization code (With PKCE)`
- Callback URL: `<Use the one you have set up in your app client>`
- Authorize using browser: Disabled
- Auth URL: `https://<user_pool_id>.auth.<region>.amazoncognito.com/oauth2/authorize`
- Access Token URL: `https://<user_pool_id>.auth.<region>.amazoncognito.com/oauth2/token`
- Client ID: `<your app client id>`
- Code Challenge Method: SHA-256
- Scope: `phone openid email`
- Client authentication : Send client credentials in body

**Advanced**
- Refresh Token URL: `https://<user_pool_id>.auth.<region>.amazoncognito.com/oauth2/token`
- Refresh request: key=`client_id`, value=`<client_id>` for request body.
