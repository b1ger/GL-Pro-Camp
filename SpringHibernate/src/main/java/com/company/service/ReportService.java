package com.company.service;

import com.company.domain.Material;
import com.company.domain.Report;
import com.company.domain.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReportService extends CommonCrudService {

    private final SessionFactory sessionFactory;

    public ReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Report> findOne(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hqlQuery = "FROM reports where id = :id";
            Query query = session.createQuery(hqlQuery);
            query.setParameter("id", id);
            return  query.list().size() > 0 ? Optional.of((Report)query.list().get(0)): Optional.empty();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Report> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hqlQuery = "FROM reports";
            Query query = session.createQuery(hqlQuery);
            return  query.list();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public List<Report> findAllByUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Report.class);
            criteria.add(Restrictions.eq("user", user));
            List<Report> reports = criteria.list();
            transaction.commit();
            return reports;
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
