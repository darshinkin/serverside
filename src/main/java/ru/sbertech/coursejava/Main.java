package ru.sbertech.coursejava;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.sbertech.coursejava.accountServer.AccountServer;
import ru.sbertech.coursejava.accountServer.AccountServerController;
import ru.sbertech.coursejava.accountServer.AccountServerControllerMBean;
import ru.sbertech.coursejava.accountServer.AccountServerImpl;
import ru.sbertech.coursejava.accounts.AccountService;
import ru.sbertech.coursejava.accounts.AcountServiceDBImpl;
import ru.sbertech.coursejava.chat.WebSocketChatServlet;
import ru.sbertech.coursejava.dbService.DBH2Service;
import ru.sbertech.coursejava.dbService.DBService;
import ru.sbertech.coursejava.servlets.AdminPageServlet;
import ru.sbertech.coursejava.servlets.MirrorRequestServlet;
import ru.sbertech.coursejava.servlets.SignInServlet;
import ru.sbertech.coursejava.servlets.SignUpServlet;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;

public class Main {
    static final Logger logger = getLoggerContext().getLogger(Main.class.getName());

    public static org.apache.logging.log4j.spi.LoggerContext getLoggerContext() {
        LoggerContext context = (LoggerContext) LogManager.getContext();
        Configuration config = context.getConfiguration();

        PatternLayout layout = PatternLayout.createLayout("%m%n", null, null, Charset.defaultCharset(), false, false, null, null);
        Appender appender = ConsoleAppender.createAppender(layout, null, null, "CONSOLE_APPENDER", null, null);
        appender.start();
        AppenderRef ref = AppenderRef.createAppenderRef("CONSOLE_APPENDER", null, null);
        AppenderRef[] refs = new AppenderRef[]{ref};
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.INFO, "CONSOLE_LOGGER", "com", refs, null, null, null);
        loggerConfig.addAppender(appender, null, null);

        config.addAppender(appender);
        config.addLogger("Main.class", loggerConfig);
        context.updateLoggers(config);
        return LogManager.getContext();
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;

        logger.info("Starting at http://127.0.0.1:" + port);

        DBService dbService = new DBH2Service();
        dbService.create();
        dbService.check();

        AccountService accountService = new AcountServiceDBImpl(dbService);
        logger.info("Created accountService");

        AccountServer accountServer = new AccountServerImpl(10);
        logger.info("Created accountServer");
        createMBean(accountServer);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new MirrorRequestServlet()), "/mirror");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        context.addServlet(new ServletHolder(new AdminPageServlet(accountServer)), AdminPageServlet.PAGE_URL);

        Server server = new Server(port);
        logger.info("Created server");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        server.join();
    }

    private static void createMBean(AccountServer accountServer) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);
        logger.info("Created MBean: Admin:type=AccountServerController");
    }
}
