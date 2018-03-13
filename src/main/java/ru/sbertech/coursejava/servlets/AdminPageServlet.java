package ru.sbertech.coursejava.servlets;

import org.apache.logging.log4j.Logger;
import ru.sbertech.coursejava.Main;
import ru.sbertech.coursejava.accountServer.AccountServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {

    private static final Logger logger = Main.getLoggerContext().getLogger(AdminPageServlet.class.getName());
    public static final String PAGE_URL = "/admin";
    private final AccountServer accountServer;

    public AdminPageServlet(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int limit = accountServer.getUsersLimit();

        logger.info("Limit: {}", limit);

        response.getWriter().println(limit);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
