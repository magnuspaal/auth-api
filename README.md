### Authentication API

This is an authentication api based on JWT tokens. Features:
* Register a user and get access and refresh tokens
    * Includes user email verification
* Authenticate user and get a new access and refresh token
* Refresh user token using the refresh token and receive a new access and refresh token

#### Environment arguments
```agsl
ALLOWED_ORIGINS=http://localhost:3000,http://192.168.10.225:3000
API_URL=http://192.168.10.225:8080
FROM_EMAIL=
FROM_PASSWORD=
MAIL_HOST=
MAIL_PORT=
SECRET_KEY=
FILE_SERVER_URL=http://localhost:8082
```

#### Release
* Run `./cicd/deploy/bump <version>`
* Push new commit and tag. GitHub Actions will deploy the container.
