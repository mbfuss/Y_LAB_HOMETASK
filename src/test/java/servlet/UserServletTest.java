package servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.UserService;
import org.example.servlet.UserServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;


import static org.mockito.Mockito.*;

class UserServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    private UserService userService;
    private UserServlet userServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userServlet = new UserServlet();
    }


    @Test
    void testDoPut() throws ServletException, IOException {

        // Mock данные запроса
        when(request.getMethod()).thenReturn("PUT");
        when(request.getInputStream()).thenReturn(new TestServletInputStream(
                "{ \"username\": \"admin\", \"password\": \"password\" }"));

        // Вызываем метод doPut() сервлета
        userServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDelete() throws ServletException, IOException {

        when(request.getMethod()).thenReturn("DELETE");


        userServlet.doDelete(request, response);


        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

    }


    private static class TestServletInputStream extends ServletInputStream {
        private final InputStream delegate;

        public TestServletInputStream(String input) {
            this.delegate = new ByteArrayInputStream(input.getBytes());
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}