package com.example.bariatric_mobile.models.auth;

/**
 * Data model representing user login credentials for authentication requests.
 * <p>
 * Contains user email and password required for login API endpoint.
 * Used to authenticate users and obtain access tokens.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Creates a new LoginRequest with user credentials.
     *
     * @param email User's email address for authentication
     * @param password User's password for authentication
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return Email address used for authentication
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email Email address used for authentication
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return Password used for authentication
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password Password used for authentication
     */
    public void setPassword(String password) {
        this.password = password;
    }
}