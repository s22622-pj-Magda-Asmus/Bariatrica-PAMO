package com.example.bariatric_mobile.models.auth;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void constructor_shouldSetAllFields() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";

        // When
        User user = new User(id, name, surname, email);

        // Then
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void getFullName_shouldConcatenateNameAndSurname() {
        // Given
        User user = new User(1L, "John", "Doe", "john@example.com");

        // When
        String fullName = user.getFullName();

        // Then
        assertEquals("John Doe", fullName);
    }

    @Test
    void equals_withDifferentFields_shouldReturnFalse() {
        // Given
        User user1 = new User(1L, "John", "Doe", "john@example.com");
        User user2 = new User(2L, "John", "Doe", "john@example.com");

        // When & Then
        assertNotEquals(user1, user2);
    }
}
