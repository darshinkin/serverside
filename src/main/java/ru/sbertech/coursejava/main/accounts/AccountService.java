package ru.sbertech.coursejava.main.accounts;

public interface AccountService {

    void signup(String login, String password);
    boolean signin(String login, String password);
}
