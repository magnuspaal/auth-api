### Authentication API

This is an authentication api based on JWT tokens. Features:
* Register a user and get access and refresh tokens
    * Includes user email verification
* Authenticate user and get a new access and refresh token
* Refresh user token using the refresh token and receive a new access and refresh token

#### Environment arguments
```agsl
JWT_SECRET=
```