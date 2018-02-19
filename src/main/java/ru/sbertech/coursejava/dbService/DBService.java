package ru.sbertech.coursejava.dbService;

public interface DBService<T> {

    long addUser(String login, String password) throws DBException;

    T getUser(String login) throws DBException;

    void check() throws DBException;

    void create() throws DBException;
}
