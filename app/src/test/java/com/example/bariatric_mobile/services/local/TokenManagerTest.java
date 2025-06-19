package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenManagerTest {

    @Mock
    private Context mockContext;

    @Mock
    private SharedPreferences mockPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;

    private TokenManager tokenManager;

    @BeforeEach
    public void setUp() {
        // Mock SharedPreferences behavior
        lenient().when(mockContext.getSharedPreferences("auth_preferences", Context.MODE_PRIVATE))
                .thenReturn(mockPreferences);
        lenient().when(mockPreferences.edit()).thenReturn(mockEditor);
        lenient().when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);
        lenient().when(mockEditor.remove(anyString())).thenReturn(mockEditor);

        // TokenManager will fall back to regular SharedPreferences since we can't mock EncryptedSharedPreferences easily
        tokenManager = new TokenManager(mockContext);
    }

    @Test
    public void setToken_shouldStoreTokenInPreferences() {
        String testToken = "test_auth_token_123";

        tokenManager.setToken(testToken);

        verify(mockEditor).putString("auth_token", testToken);
        verify(mockEditor).apply();
    }

    @Test
    public void getToken_shouldReturnStoredToken() {
        String expectedToken = "stored_token_456";
        when(mockPreferences.getString("auth_token", null)).thenReturn(expectedToken);

        String actualToken = tokenManager.getToken();

        assertEquals(expectedToken, actualToken);
        verify(mockPreferences).getString("auth_token", null);
    }

    @Test
    public void getToken_withNoStoredToken_shouldReturnNull() {
        when(mockPreferences.getString("auth_token", null)).thenReturn(null);

        String actualToken = tokenManager.getToken();

        assertNull(actualToken);
    }

    @Test
    public void hasToken_withValidToken_shouldReturnTrue() {
        when(mockPreferences.getString("auth_token", null)).thenReturn("valid_token");

        boolean hasToken = tokenManager.hasToken();

        assertTrue(hasToken);
    }

    @Test
    public void hasToken_withNullToken_shouldReturnFalse() {
        when(mockPreferences.getString("auth_token", null)).thenReturn(null);

        boolean hasToken = tokenManager.hasToken();

        assertFalse(hasToken);
    }

    @Test
    public void hasToken_withEmptyToken_shouldReturnFalse() {
        when(mockPreferences.getString("auth_token", null)).thenReturn("");

        boolean hasToken = tokenManager.hasToken();

        assertFalse(hasToken);
    }

    @Test
    public void hasToken_withWhitespaceOnlyToken_shouldReturnFalse() {
        when(mockPreferences.getString("auth_token", null)).thenReturn("   ");

        boolean hasToken = tokenManager.hasToken();

        assertFalse(hasToken);
    }

    @Test
    public void removeToken_shouldDeleteTokenFromPreferences() {
        tokenManager.removeToken();

        verify(mockEditor).remove("auth_token");
        verify(mockEditor).apply();
    }

    @Test
    public void tokenLifecycle_setGetRemove_shouldWorkCorrectly() {
        String testToken = "lifecycle_test_token";

        // Initially no token
        when(mockPreferences.getString("auth_token", null)).thenReturn(null);
        assertFalse(tokenManager.hasToken());

        // Set token
        tokenManager.setToken(testToken);
        verify(mockEditor).putString("auth_token", testToken);

        // Token exists
        when(mockPreferences.getString("auth_token", null)).thenReturn(testToken);
        assertTrue(tokenManager.hasToken());
        assertEquals(testToken, tokenManager.getToken());

        // Remove token
        tokenManager.removeToken();
        verify(mockEditor).remove("auth_token");

        // Token no longer exists
        when(mockPreferences.getString("auth_token", null)).thenReturn(null);
        assertFalse(tokenManager.hasToken());
    }

    @Test
    public void hasToken_afterSettingNullToken_shouldReturnFalse() {
        when(mockPreferences.getString("auth_token", null)).thenReturn(null);

        boolean hasToken = tokenManager.hasToken();

        assertFalse(hasToken);
    }
}