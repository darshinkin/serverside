package ru.sbertech.coursejava.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.sbertech.coursejava.main.accounts.AccountServiceImpl;
import ru.sbertech.coursejava.servlets.MirrorRequestServlet;
import ru.sbertech.coursejava.servlets.SignInServlet;
import ru.sbertech.coursejava.servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountServiceImpl accountService = new AccountServiceImpl();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new MirrorRequestServlet()), "/mirror");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
