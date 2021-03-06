package com.pd.api.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PropertiesLoader implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO: Nothing really
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            FileInputStream configFile = new FileInputStream(getClass().getClassLoader().getResource("config.properties").getFile());
            Properties p = new Properties(System.getProperties());
            p.load(configFile);
            System.setProperties(p);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
