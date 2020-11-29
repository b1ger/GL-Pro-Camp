package com.company.service;

import com.company.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService extends CommonCrudService {

    private final SessionFactory sessionFactory;

    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> findOne(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hqlQuery = "FROM users where id = :id";
            Query query = session.createQuery(hqlQuery);
            query.setParameter("id", id);
            transaction.commit();
            return  query.list().size() > 0 ? Optional.of((User)query.list().get(0)): Optional.empty();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hqlQuery = "FROM users";
            Query query = session.createQuery(hqlQuery);
            transaction.commit();
            return  query.list();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
