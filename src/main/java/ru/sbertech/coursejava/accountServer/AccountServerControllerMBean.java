package ru.sbertech.coursejava.accountServer;

public interface AccountServerControllerMBean {
    int getUsersLimit();

    void setUsersLimit(int usersLimit);
}
