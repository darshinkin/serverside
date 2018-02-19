package ru.sbertech.coursejava.servlets;

import ru.sbertech.coursejava.accounts.AccountService;
import ru.sbertech.coursejava.accounts.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(AccountServiceImpl.LOGIN);
        String pass = request.getParameter(AccountServiceImpl.PASSWORD);

        boolean loggedIn = false;
        loggedIn = accountService.signin(login, pass);
        response.setContentType("text/html;charset=utf-8");
        if (loggedIn) {
            response.getWriter().println(String.format("Authorized: %s", login));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
