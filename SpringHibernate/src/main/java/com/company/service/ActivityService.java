package com.company.service;

import com.company.domain.Activity;
import com.company.domain.Building;
import com.company.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActivityService extends CommonCrudService {

    private final SessionFactory sessionFactory;

    public ActivityService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional findOne(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String sqlQuery = "SELECT * FROM activities where id = :id";
            Query query = session.createSQLQuery(sqlQuery);
            query.setParameter("id", id);
            Optional optional =  query.list().size() > 0 ? Optional.of((User)query.list().get(0)): Optional.empty();
            transaction.commit();
            return optional;
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
            String sqlQuery = "SELECT * FROM activities";
            Query query = session.createSQLQuery(sqlQuery);
            List list = query.list();
            transaction.commit();
            return list;
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public List findAllByUserAndBuilding(User user, Building building) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Activity> cq = cb.createQuery(Activity.class);
            // TODO
            return null;
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
