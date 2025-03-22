A service for creating and listing notes for users.

## Setup

### Local
In order to run this locally, you need to add an `application-local.properties` file in the resources folder with the following properties:
- `COGNITO_ISSUER_URI`: The issuer URI for the Cognito user pool
- `AWS_PROFILE`: The AWS profile to use for the AWS SDK

And then set the CLI arguments to `--spring.profiles.active=local`. 

_This can also be achieved by using environment variables, but for a consistent dev environment it is recommended to use the properties file._

### Cloud
Similarly, deploying this to the cloud requires you to set the same properties using environment variables.
