package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS user.User (id int primary key auto_increment," +
                " name varchar(55)," +
                " lastName varchar(55)," +
                " age int);").addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("DROP TABLE IF EXISTS user.User;").addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));
        System.out.println(name + "add to table with HN");

        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User userForDelete = session.get(User.class, id);
        session.remove(userForDelete);

        transaction.commit();
        System.out.println(userForDelete.getName() + "was deleted from table");
        session.close();


    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> result = session.createQuery("from User", User.class).getResultList();
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM User").executeUpdate();
        System.out.println("table was clear()");
        transaction.commit();
        session.close();


    }
}
