A service for creating and listing notes for users.

## Setup

_WIP: This whole readme is a WIP, it might miss certain things and is not properly structured._

### Running locally

1. Create shared dynamodb and keycloak data with:
    ```bash
    docker volume create goat-notes-dynamodb-data && docker volume create shared-keycloak-data
    ```

2. Spin up the containers for keycloak and dynamodb:
    ```bash
    docker compose -f docker/dev/docker-compose.yml up -d
    ```

3. Provision DynamoDb and Keycloak
   - Wait until DynamoDB is running, then create necessary table(s).
   - Set up realms, clients, and users as needed in Keycloak.


4. Set the following environment variables:
   ```
   OAUTH_CLIENT_ID=...
   OAUTH_CLIENT_SECRET=...
   ```

### Running in production

1. Provision DynamoDB and AWS Cognito

   - Use AWS Console or CLI to create the required table(s).
   - Set up clients, and users as needed.


2. Set the following environment variables:
   ```
   PORT=...
   AWS_REGION=...
   AWS_PROFILE=...
   OAUTH_CLIENT_ID=...
   OAUTH_CLIENT_SECRET=...
   OAUTH_REDIRECT_URI=...
   OAUTH_ISSUER_URI=...
   OAUTH_JWK_URI=...
   OAUTH_AUTH_URI=...
   OAUTH_TOKEN_URI=...
   OAUTH_LOGOUT_URI=...
   ```
   
### Usage
Open `http://localhost:<PORT>/ui/notes` in your browser or access the API directly at `http://localhost:<PORT>/notes`.