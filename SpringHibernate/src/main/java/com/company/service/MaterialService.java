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

public class MaterialService extends CommonCrudService {

    private final SessionFactory sessionFactory;

    public MaterialService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional findOne(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String sqlQuery = "SELECT * FROM materials where id = :id";
            Query query = session.createSQLQuery(sqlQuery);
            query.setParameter("id", id);
            transaction.commit();
            return  query.list().size() > 0 ? Optional.of((User)query.list().get(0)): Optional.empty();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected error.", ex);
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
            String sqlQuery = "SELECT * FROM materials";
            Query query = session.createSQLQuery(sqlQuery);
            transaction.commit();
            return  query.list();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected error.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
