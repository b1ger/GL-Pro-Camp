package com.company.util;

import com.company.domain.Activity;
import com.company.domain.Building;
import com.company.domain.Report;
import com.company.domain.User;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FillDatabaseUtilImpl implements FillDatabaseUtil {

    private static final Logger logger = LogManager.getLogger(FillDatabaseUtilImpl.class);

    private final SessionFactory sessionFactory;
    private final Faker faker = new Faker();

    private static final int USERS = 10;
    private static final int REPORTS = 30;
    private static final int BUILDINGS = 60;
    private static final int ACTIVITIES = 120;

    public FillDatabaseUtilImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void initData() {

        List<User> users = new ArrayList<>();
        List<Report> reports = new ArrayList<>();
        List<Building> buildings = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();

        fillUsers(users);
        fillReports(reports);
        fillBuildings(buildings);
        fillActivities(activities);

        bindBuildingsToActivities(buildings, activities);
        bindReportsToBuildings(reports, buildings);
        bindUsersToReports(users, reports);

        Session session = sessionFactory.openSession();

        saveEntities(users, session);

//        saveEntities(reports, session);
//        saveEntities(buildings, session);
//        saveEntities(activities, session);
    }

    private void saveEntities(List entities, Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            entities.forEach(session::persist);
            transaction.commit();
            logger.debug(entities.get(0).getClass().getName() + " entities ware saved successfully.");
        } catch (HibernateException ex) {
            logger.error("Unexpected exception.", ex);
            transaction.rollback();
        }
    }

    private void fillUsers(List<User> users) {
        for (int i = 0; i < USERS; i++) {
            users.add(new User());
        }
        users.forEach(user -> {
            user.setUserName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(user.getUserName() + "@mail.com");
        });
    }

    private void fillReports(List<Report> reports) {
        for (int i = 0; i < REPORTS; i++) {
            reports.add(new Report());
        }
        reports.forEach(report -> {
            report.setName("Report #" + faker.number().numberBetween(1, 999));
            report.setName(faker.lorem().characters(8, 16));
            report.setPrice(faker.number().randomDouble(2, 1000, 1000000));
            report.setOrderDate(faker.date().between(new Date(System.currentTimeMillis() - 1000000000000L), new Date()));
        });
    }

    private void fillBuildings(List<Building> buildings) {
        for (int i = 0; i < BUILDINGS; i++) {
            buildings.add(new Building());
        }
        buildings.forEach(building -> {
            building.setName("Building #" + faker.number().numberBetween(1, 999));
            building.setActive(faker.bool().bool());
        });
    }

    private void fillActivities(List<Activity> activities) {
        for (int i = 0; i < ACTIVITIES; i++) {
            activities.add(new Activity());
        }
        activities.forEach(activity -> {
            activity.setWorkName(faker.lorem().characters(8, 16));
            activity.setMeasurement(faker.lorem().characters(8, 16));
            activity.setPrice(faker.number().randomDouble(2, 1000, 1000000));
            activity.setAmount(faker.number().randomDouble(2, 1000, 1000000));
        });
    }

    private void bindBuildingsToActivities(List<Building> buildings, List<Activity> activities) {
        for (int i = 0; i < buildings.size(); i++) {
            buildings.get(i).addActivity(activities.get(i));
            buildings.get(i).addActivity(activities.get(activities.size() - i - 1));
            activities.get(i).setBuilding(buildings.get(i));
            activities.get(activities.size() - i - 1).setBuilding(buildings.get(i));
        }
    }

    private void bindReportsToBuildings(List<Report> reports, List<Building> buildings) {
        for (int i = 0; i < reports.size(); i++) {
            reports.get(i).addBuilding(buildings.get(i));
            reports.get(i).addBuilding(buildings.get(buildings.size() - i - 1));
            buildings.get(i).setReport(reports.get(i));
            buildings.get(buildings.size() - i - 1).setReport(reports.get(i));
        }
    }

    private void bindUsersToReports(List<User> users, List<Report> reports) {
        int userIndex = 0;
        for (int i = 0; i < reports.size(); i++) {
            if (i % 3 == 0 && i > 0) {
                userIndex++;
            }
            reports.get(i).setUser(users.get(userIndex));
            users.get(userIndex).addReport(reports.get(i));
        }
    }
}
