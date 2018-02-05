package ru.sbertech.coursejava.main.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    private final Map<String, UserProfile> signedUpUsers;

    public AccountServiceImpl() {
        signedUpUsers = new HashMap<>();
    }

    @Override
    public void signup(String login, String password) {
        UserProfile userProfile = new UserProfile(login, password, login);
        signedUpUsers.put(login, userProfile);
    }

    @Override
    public boolean signin(String login, String password) {
        UserProfile userProfile = signedUpUsers.get(login);
        return userProfile != null && userProfile.getPass().equals(password);
    }
}
