package ru.sbertech.coursejava;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.sbertech.coursejava.accounts.AccountService;
import ru.sbertech.coursejava.accounts.AcountServiceDBImpl;
import ru.sbertech.coursejava.dbService.DBH2Service;
import ru.sbertech.coursejava.dbService.DBService;
import ru.sbertech.coursejava.servlets.MirrorRequestServlet;
import ru.sbertech.coursejava.servlets.SignInServlet;
import ru.sbertech.coursejava.servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBH2Service();
        dbService.create();
        dbService.check();

        AccountService accountService = new AcountServiceDBImpl(dbService);
        System.out.println("Created accountService");

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
