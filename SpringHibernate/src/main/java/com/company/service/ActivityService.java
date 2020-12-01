package com.company.service;

import com.company.domain.Building;
import com.company.domain.Report;
import com.company.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            String sqlQuery = "SELECT\n" +
                              "    activities.*\n" +
                              "FROM\n" +
                              "    activities\n" +
                              "JOIN buildings \n" +
                              "   ON activities.building_id = buildings.id\n" +
                              "Join reports\n" +
                              "   ON buildings.report_id = reports.id\n" +
                              "WHERE building_id = :build_id and reports.user_id = :user_id";

            Query query = session.createSQLQuery(sqlQuery);
            query.setParameter("build_id", building.getId());
            query.setParameter("user_id", user.getId());

            return query.list();
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Double getTotalPriceByUserAndBuildingAndReport(User user, Building building, Report report) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String sqlQuery = "SELECT\n" +
                              "    sum(activities.price)" +
                              "FROM\n" +
                              "    activities\n" +
                              "JOIN buildings \n" +
                              "   ON activities.building_id = buildings.id\n" +
                              "Join reports\n" +
                              "   ON buildings.report_id = reports.id\n" +
                              "WHERE building_id = :build_id and reports.user_id = :user_id and reports.id = :report_id";

            Query query = session.createSQLQuery(sqlQuery);
            query.setParameter("build_id", building.getId());
            query.setParameter("user_id", user.getId());
            query.setParameter("report_id", report.getId());

            return (Double) query.list().get(0);
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return 0d;
        } finally {
            session.close();
        }
    }
}
