package ru.sbertech.coursejava.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.sbertech.coursejava.dbService.UsersDataSet;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public UsersDataSet getUser(String login) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria.add(Restrictions.eq("name", login)).uniqueResult();
    }

    public long insertUser(String name, String password) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        UsersDataSet usersDataSet = (UsersDataSet) criteria.add(Restrictions.eq("name", name)).
                add(Restrictions.eq("password", password)).uniqueResult();
        if (usersDataSet != null) {
            return usersDataSet.getId();
        }
        return (Long)session.save(new UsersDataSet(name, password));
    }

    public UsersDataSet get(String login, String password) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria.add(Restrictions.eq("name", login)).
                add(Restrictions.eq("password", password)).uniqueResult();
    }
}
