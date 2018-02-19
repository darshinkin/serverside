package ru.sbertech.coursejava.servlets;

import ru.sbertech.coursejava.accounts.AccountService;
import ru.sbertech.coursejava.dbService.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.sbertech.coursejava.accounts.AccountServiceImpl.LOGIN;
import static ru.sbertech.coursejava.accounts.AccountServiceImpl.PASSWORD;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            accountService.signup(login, pass);
        } catch (DBException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("User has been registrated!!!");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
