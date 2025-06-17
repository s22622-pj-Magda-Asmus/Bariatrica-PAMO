package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bariatric_mobile.models.auth.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionManagerTest {

    @Mock
    private Context mockContext;

    @Mock
    private SharedPreferences mockPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;

    private SessionManager sessionManager;
    private User testUser;

    @BeforeEach
    public void setUp() {
        // Mock SharedPreferences behavior
        lenient().when(mockContext.getSharedPreferences("session_preferences", Context.MODE_PRIVATE))
                .thenReturn(mockPreferences);
        lenient().when(mockPreferences.edit()).thenReturn(mockEditor);
        lenient().when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);
        lenient().when(mockEditor.remove(anyString())).thenReturn(mockEditor);

        // Create test user
        testUser = new User(1L, "John", "Doe", "john.doe@example.com");

        // SessionManager will fall back to regular SharedPreferences
        sessionManager = new SessionManager(mockContext);
    }

    @Test
    public void storeUser_shouldSerializeAndStoreUser() {
        sessionManager.storeUser(testUser);

        // Verify that a JSON string was stored (we can't easily verify exact JSON due to Gson)
        verify(mockEditor).putString(eq("auth_user"), anyString());
        verify(mockEditor).apply();
    }

    @Test
    public void storeUser_withNullUser_shouldNotStoreAnything() {
        sessionManager.storeUser(null);

        // Should not interact with preferences when user is null
        verify(mockEditor, never()).putString(anyString(), anyString());
        verify(mockEditor, never()).apply();
    }

    @Test
    public void getUser_withStoredUser_shouldDeserializeAndReturnUser() {
        // Mock stored JSON for the test user
        String userJson = "{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\"}";
        when(mockPreferences.getString("auth_user", null)).thenReturn(userJson);

        User retrievedUser = sessionManager.getUser();

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals("John", retrievedUser.getName());
        assertEquals("Doe", retrievedUser.getSurname());
        assertEquals("john.doe@example.com", retrievedUser.getEmail());
        verify(mockPreferences).getString("auth_user", null);
    }

    @Test
    public void getUser_withNoStoredUser_shouldReturnNull() {
        when(mockPreferences.getString("auth_user", null)).thenReturn(null);

        User retrievedUser = sessionManager.getUser();

        assertNull(retrievedUser);
    }

    @Test
    public void getUser_withEmptyStoredData_shouldReturnNull() {
        when(mockPreferences.getString("auth_user", null)).thenReturn("");

        User retrievedUser = sessionManager.getUser();

        assertNull(retrievedUser);
    }



    @Test
    public void hasUser_withStoredUser_shouldReturnTrue() {
        String userJson = "{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\"}";
        when(mockPreferences.getString("auth_user", null)).thenReturn(userJson);

        boolean hasUser = sessionManager.hasUser();

        assertTrue(hasUser);
    }

    @Test
    public void hasUser_withNoStoredUser_shouldReturnFalse() {
        when(mockPreferences.getString("auth_user", null)).thenReturn(null);

        boolean hasUser = sessionManager.hasUser();

        assertFalse(hasUser);
    }

    @Test
    public void hasUser_withEmptyStoredData_shouldReturnFalse() {
        when(mockPreferences.getString("auth_user", null)).thenReturn("");

        boolean hasUser = sessionManager.hasUser();

        assertFalse(hasUser);
    }

    @Test
    public void clearUser_shouldRemoveUserFromPreferences() {
        sessionManager.clearUser();

        verify(mockEditor).remove("auth_user");
        verify(mockEditor).apply();
    }

    @Test
    public void userSessionLifecycle_storeGetClear_shouldWorkCorrectly() {
        // Initially no user
        when(mockPreferences.getString("auth_user", null)).thenReturn(null);
        assertFalse(sessionManager.hasUser());
        assertNull(sessionManager.getUser());

        // Store user
        sessionManager.storeUser(testUser);
        verify(mockEditor).putString(eq("auth_user"), anyString());

        // User exists
        String userJson = "{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\"}";
        when(mockPreferences.getString("auth_user", null)).thenReturn(userJson);
        assertTrue(sessionManager.hasUser());

        User retrievedUser = sessionManager.getUser();
        assertNotNull(retrievedUser);
        assertEquals("John", retrievedUser.getName());
        assertEquals("john.doe@example.com", retrievedUser.getEmail());

        // Clear user
        sessionManager.clearUser();
        verify(mockEditor).remove("auth_user");

        // User no longer exists
        when(mockPreferences.getString("auth_user", null)).thenReturn(null);
        assertFalse(sessionManager.hasUser());
        assertNull(sessionManager.getUser());
    }

    @Test
    public void storeUser_multipleUsers_shouldOverwritePrevious() {
        User firstUser = new User(1L, "John", "Doe", "john@example.com");
        User secondUser = new User(2L, "Jane", "Smith", "jane@example.com");

        // Store first user
        sessionManager.storeUser(firstUser);
        verify(mockEditor, times(1)).putString(eq("auth_user"), anyString());

        // Store second user (should overwrite)
        sessionManager.storeUser(secondUser);
        verify(mockEditor, times(2)).putString(eq("auth_user"), anyString());
        verify(mockEditor, times(2)).apply();
    }
}