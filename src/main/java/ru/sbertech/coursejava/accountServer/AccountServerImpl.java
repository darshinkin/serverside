package ru.sbertech.coursejava.accountServer;

public class AccountServerImpl implements AccountServer {
    private int usersLimit;

    public AccountServerImpl(int usersLimit) {
        this.usersLimit = usersLimit;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
