package com.company;

import com.company.config.HibernateConfig;
import com.company.domain.Activity;
import com.company.domain.Building;
import com.company.domain.Report;
import com.company.domain.User;
import com.company.service.ActivityService;
import com.company.service.BuildingService;
import com.company.service.ReportService;
import com.company.service.UserService;
import com.company.util.FillDatabaseUtil;
import com.company.util.FillDatabaseUtilImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        System.out.println("Hello World!");

        SessionFactory factory = HibernateConfig.getSessionFactory();

        FillDatabaseUtil faker = new FillDatabaseUtilImpl(factory);
        faker.initData();

        UserService userService = new UserService(factory);
        ReportService reportService = new ReportService(factory);
        BuildingService buildingService = new BuildingService(factory);
        ActivityService activityService = new ActivityService(factory);

        Optional<User> userOptional = userService.findOne(1l);
        User user = userOptional.get();
        System.out.println(user);
        logger.debug(user.toString());

        // Get all Reports information for particular user
        List<Report> reports = reportService.findAllByUser(user);
        System.out.println(reports);
        logger.debug(reports.toString());

        // Get All activities information for Particular user and Specified building
        Optional<Building> buildingOptional = buildingService.findOne(2l);
        Building building = buildingOptional.get();
        List<Activity> activities = activityService.findAllByUserAndBuilding(user, building);
        System.out.println(activities);

        // Set Buildings in non-Active state where total price of all activities is more than specified value
        boolean result = buildingService.disActiveIfPriceLessThan(1000000);
        System.out.println(result);
        logger.debug(result);

        // Get total Activities price for particular building/report/user.
        Double total = activityService.getTotalPriceByUserAndBuildingAndReport(user, building, reports.get(0));
        System.out.println(total);
        logger.debug(total);

        factory.close();
    }
}
