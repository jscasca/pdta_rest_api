package com.pd.api.db;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;

public class DataBaseBuilder {

    protected static String serverName = "localhost";
    protected static String schema = "posdta_1_0";
    
    private DataBaseBuilder() {};
    
    public static Configuration config() {
        AnnotationConfiguration ac = new AnnotationConfiguration();
        
        //ac.addAnnotatedClass(Author.class);
        ac.addAnnotatedClass(Credential.class);
        ac.addAnnotatedClass(Role.class);
        ac.addAnnotatedClass(User.class);
        
        ac.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        ac.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        ac.setProperty("hibernate.connection.url", "jdbc:mysql://" + serverName +  "/" + schema + "?useUnicode=true&amp;characterEncoding=UTF-8");
        ac.setProperty("hibernate.connection.username", "root");
        ac.setProperty("hibernate.connection.password", "root");
        
        return ac;
    }
    
    public static void runCreate(boolean exec) {
        SchemaExport se = new SchemaExport(config());
        se.setFormat(true);
        se.setDelimiter(";");
        se.setOutputFile("src/test/resources/posdta.sql");
        se.create(true, exec);
    }
    
    public static void runUpdate(boolean exec) {
        SchemaUpdate su = new SchemaUpdate(config());
        su.setFormat(true);
        su.setDelimiter(";");
        su.setOutputFile("src/main/db/posdta-update.sql");
        su.execute(true, exec);
    }
    
    public static void main(String[] args) {
        runCreate(false);
        //runUpdate(false);
    }
}
