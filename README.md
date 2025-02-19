A service for creating and listing notes for users.

### Setup

In order to run this locally, you need to add an `application-local.properties` file in the resources folder with the following properties:
- COGNITO_ISSUER_URI

And then set the CLI arguments to `--spring.profiles.active=local`. 

Similarly, deploying this to the cloud requires you to set the same properties in the environment variables.