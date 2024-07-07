package servlet;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class UserServletTest {

    private UserService userService;
    private UserServlet userServlet;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userServlet = new UserServlet(userService);
    }

    @Test
    void doPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String json = "{\"username\":\"john\",\"password\":\"password\",\"isAdmin\":true}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doPost(request, response);

        verify(userService, times(1)).registerUser("john", "password", true);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }
}
