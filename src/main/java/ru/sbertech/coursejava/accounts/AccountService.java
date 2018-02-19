package ru.sbertech.coursejava.accounts;

import ru.sbertech.coursejava.dbService.DBException;

public interface AccountService {

    void signup(String login, String password) throws DBException;
    boolean signin(String login, String password);
}
