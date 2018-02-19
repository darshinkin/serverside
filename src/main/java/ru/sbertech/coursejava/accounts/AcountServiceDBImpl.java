package ru.sbertech.coursejava.accounts;

import ru.sbertech.coursejava.dbService.DBException;
import ru.sbertech.coursejava.dbService.DBService;
import ru.sbertech.coursejava.dbService.UsersDataSet;

public class AcountServiceDBImpl implements AccountService {

    private DBService dbService;

    public AcountServiceDBImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void signup(String login, String password) throws DBException {
        dbService.addUser(login, password);
    }

    @Override
    public boolean signin(String login, String password) {
        UsersDataSet usersDataSet = null;
        try {
            usersDataSet = (UsersDataSet) dbService.getUser(login);
            return usersDataSet != null && usersDataSet.getPassword().equals(password);
        } catch (DBException e) {
            System.out.println("Can't sing in: " + e.getMessage());
            return false;
        }
    }
}
