package ru.sbertech.coursejava.servlets;

import org.junit.Test;
import ru.sbertech.coursejava.accountServer.AccountServer;
import ru.sbertech.coursejava.accountServer.AccountServerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminPageServletTest {

    private AccountServer accountServer = mock(AccountServerImpl.class);

    @Test
    public void testLimit() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(AdminPageServlet.PAGE_URL);

        when(accountServer.getUsersLimit()).thenReturn(10);

        AdminPageServlet adminPageServlet = new AdminPageServlet(accountServer);
        adminPageServlet.doGet(request, response);

        assertEquals("10", stringWriter.toString().trim());
        verify(accountServer, only()).getUsersLimit();
    }

    private HttpServletRequest getMockedRequest(String url) {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn(url);

        return request;
    }

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        return response;
    }
}