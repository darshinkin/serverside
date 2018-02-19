package ru.sbertech.coursejava.dbService;

import org.h2.jdbcx.JdbcDataSource;
import ru.sbertech.coursejava.dao.UsersH2Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBH2Service implements DBService<UsersDataSet> {

    private final Connection connection;

    public DBH2Service() {
        this.connection = getH2Connection();
    }

    public void create() throws DBException {
        try {
            System.out.println("Creating table users if needed");
            (new UsersH2Dao(connection)).createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public long addUser(String login, String password) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersH2Dao dao = new UsersH2Dao(connection);
            dao.insertUser(new UsersDataSet(login, password));
            connection.commit();
            return dao.getUserId(login);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public UsersDataSet getUser(String login) throws DBException {
        UsersH2Dao usersH2Dao = new UsersH2Dao(connection);
        UsersDataSet usersDataSet = null;
        try {
            usersDataSet = usersH2Dao.get(login);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return usersDataSet;
    }

    public void check() throws DBException {
        try {
            System.out.println("Driver name: " + connection.getMetaData().getDriverName());
            System.out.println("Driver version: " + connection.getMetaData().getDriverVersion());

            UsersH2Dao dao = new UsersH2Dao(connection);
            int count = dao.getUsersCount();
            System.out.println("Count of records in users: " + count);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        UsersH2Dao dao = new UsersH2Dao(connection);
        try {
            dao.cleanup();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
