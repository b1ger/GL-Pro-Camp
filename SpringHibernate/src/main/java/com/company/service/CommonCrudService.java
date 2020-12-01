package com.company.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.company.config.HibernateConfig.sessionFactory;

public abstract class CommonCrudService implements CrudService {

    Logger logger = LogManager.getLogger(this.getClass());

    public abstract Optional findOne(long id);

    public  abstract List findAll();

    public boolean saveOrUpdate(Object o) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.commit();
            session.saveOrUpdate(o);
            return true;
        } catch (HibernateException ex) {
            transaction.rollback();
            logger.error("Unexpected exception.", ex);
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Object o) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.commit();
            session.delete(o);
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
