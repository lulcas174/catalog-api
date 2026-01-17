package com.catalog.api.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyInterceptorTest {

    @InjectMocks
    private ApiKeyInterceptor apiKeyInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    void shouldReturnTrueWhenApiKeyIsValid() throws Exception {
        ReflectionTestUtils.setField(apiKeyInterceptor, "apiKey", "aXRhw7o=");
        when(request.getHeader("api-key")).thenReturn("aXRhw7o=");

        boolean result = apiKeyInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenApiKeyIsMissing() throws Exception {
        ReflectionTestUtils.setField(apiKeyInterceptor, "apiKey", "aXRhw7o=");
        when(request.getHeader("api-key")).thenReturn(null);

        boolean result = apiKeyInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void shouldReturnFalseWhenApiKeyIsInvalid() throws Exception {
        ReflectionTestUtils.setField(apiKeyInterceptor, "apiKey", "aXRhw7o=");
        when(request.getHeader("api-key")).thenReturn("invalid-key");

        boolean result = apiKeyInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
