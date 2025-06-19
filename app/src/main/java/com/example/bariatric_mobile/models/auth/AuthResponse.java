package com.example.bariatric_mobile.models.auth;

/**
 * Data model representing successful authentication response from the API.
 *
 * Contains authenticated user information and access token required
 * for subsequent API requests. Returned by login and token refresh endpoints.
 */
public class AuthResponse {
    private User user;
    private String token;

    /**
     * Creates a new AuthResponse with user data and access token.
     *
     * @param user Authenticated user information
     * @param token Access token for API authentication
     */
    public AuthResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    /**
     * Returns the authenticated user information.
     *
     * @return User object containing user details
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the authenticated user information.
     *
     * @param user User object containing user details
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the access token for API authentication.
     *
     * @return JWT access token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the access token for API authentication.
     *
     * @param token JWT access token string
     */
    public void setToken(String token) {
        this.token = token;
    }
}