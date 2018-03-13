package ru.sbertech.coursejava.accountServer;

public class AccountServerController implements AccountServerControllerMBean {

    private AccountServer accountServer;

    public AccountServerController(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    public int getUsersLimit() {
        return accountServer.getUsersLimit();
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        accountServer.setUsersLimit(usersLimit);
    }
}
