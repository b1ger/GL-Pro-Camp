package com.company;

import com.company.config.HibernateConfig;
import com.company.domain.Report;
import com.company.domain.User;
import com.company.service.BuildingService;
import com.company.service.ReportService;
import com.company.service.UserService;
import com.company.util.FillDatabaseUtil;
import com.company.util.FillDatabaseUtilImpl;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        SessionFactory factory = HibernateConfig.getSessionFactory();

        FillDatabaseUtil faker = new FillDatabaseUtilImpl(factory);
        faker.initData();

        UserService userService = new UserService(factory);
        ReportService reportService = new ReportService(factory);
        BuildingService buildingService = new BuildingService(factory);

        Optional<User> userOptional = userService.findOne(1l);
        User user = userOptional.get();
        System.out.println(user);

        // Get all Reports information for particular user
        List<Report> reports = reportService.findAllByUser(user);
        System.out.println(reports);

        // Get All activities information for Particular user and Specified building
        // TODO

        // Set Buildings in non-Active state where total price of all activities is more than specified value
        boolean result = buildingService.disActiveIfPriceLessThan(1000000);
        System.out.println(result);

        factory.close();
    }
}
