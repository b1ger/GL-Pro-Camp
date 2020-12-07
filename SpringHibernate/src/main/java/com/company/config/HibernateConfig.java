package com.company.config;

import com.company.domain.Activity;
import com.company.domain.Building;
import com.company.domain.Material;
import com.company.domain.Report;
import com.company.domain.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateConfig {

    private static final Logger logger = LogManager.getLogger(HibernateConfig.class);

    public static SessionFactory sessionFactory;

    private static final String PROPERTY_FILE = "hibernate.properties";

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            configureSessionFactory();
        }
        return sessionFactory;
    }

    private static void configureSessionFactory() {
        Configuration cfg = new Configuration();
        try (InputStream inputStream = HibernateConfig.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            Properties props = new Properties();
            props.load(inputStream);
            cfg.setProperties(props);
            sessionFactory = cfg.addAnnotatedClass(User.class)
                                .addAnnotatedClass(Report.class)
                                .addAnnotatedClass(Building.class)
                                .addAnnotatedClass(Activity.class)
                                .addAnnotatedClass(Material.class)
                                .buildSessionFactory();
        } catch (IOException e) {
            logger.error("Unexpected exception.", e);
        }
    }
}
