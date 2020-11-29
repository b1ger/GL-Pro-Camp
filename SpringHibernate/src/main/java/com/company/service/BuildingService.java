package com.company.service;

import com.company.domain.Activity;
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
import java.util.Set;

public class BuildingService extends CommonCrudService {

    private final SessionFactory sessionFactory;

    public BuildingService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional findOne(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hqlQuery = "FROM buildings where id = :id";
            Query query = session.createQuery(hqlQuery);
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
            String hqlQuery = "FROM buildings";
            Query query = session.createQuery(hqlQuery);
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

    public boolean disActiveIfPriceLessThan(double price) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Building> list = findAll();
            list.stream().filter(Building::isActive).forEach(building -> {
                Set<Activity> activities = building.getActivities();
                double priceL = activities.stream().mapToDouble(Activity::getPrice).sum();
                if (priceL > price) {
                    building.setActive(false);
                    session.saveOrUpdate(building);
                }
            });
            transaction.commit();
            return true;
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return false;
        } finally {
            session.close();
        }
    }

}
