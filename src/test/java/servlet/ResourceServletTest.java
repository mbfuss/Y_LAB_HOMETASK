package servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.servlet.ResourceServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;

import static org.mockito.Mockito.*;

class ResourceServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private ResourceServlet resourceServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceServlet = new ResourceServlet();
    }


    @Test
    void testDoPost() throws ServletException, IOException {

        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(new TestServletInputStream(
                "{ \"id\": \"3\", \"name\": \"Meeting Room C\", \"conferenceRoom\": true }"));


        resourceServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Только администраторы.");

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
