package com.example.bariatric_mobile.models.auth;

/**
 * Data model representing an authenticated user.
 * <p>
 * Contains user identification and contact information
 * used throughout the application for authentication
 * and user interface personalization.
 */
public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;

    /**
     * Creates a new User instance with all required fields.
     *
     * @param id Unique user identifier
     * @param name User's first name
     * @param surname User's last name
     * @param email User's email address
     */
    public User(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    /**
     * Returns the unique user identifier.
     *
     * @return User ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique user identifier.
     *
     * @param id User ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's first name.
     *
     * @return First name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's first name.
     *
     * @param name First name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's last name.
     *
     * @return Last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the user's last name.
     *
     * @param surname Last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the user's email address.
     *
     * @return Email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email Email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's full name by concatenating first and last name.
     *
     * @return Full name as "firstName lastName"
     */
    public String getFullName() {
        return name + " " + surname;
    }
}