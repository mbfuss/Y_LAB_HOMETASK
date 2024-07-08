package servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.servlet.BookingServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;


import static org.mockito.Mockito.*;

class BookingServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private BookingServlet bookingServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingServlet = new BookingServlet();
    }



    @Test
    void testDoPost() throws ServletException, IOException {

        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(new TestServletInputStream(
                "{ \"userId\": \"1\", \"resourceId\": \"1\", \"startTime\": \"2024-07-08T10:00:00\", \"endTime\": \"2024-07-08T12:00:00\" }"));

        // Perform doPost
        bookingServlet.doPost(request, response);

        // Validate response status
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        // Add more validation if needed based on the scenario
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
