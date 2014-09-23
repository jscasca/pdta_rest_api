package com.pd.api;

import com.pd.api.db.DAO;

public class PSDT {

    public static final String DB_VERSION = "1_0";
    public static final String DB_URL = "jdbc:mysql://"+System.getProperty("JDBC_CONNECTION_STRING","localhost")+"/posdta_"+DB_VERSION+"?useUnicode=true&amp;characterEncoding=UTF-8";
    public static String[] DB_URLS = null;
    public static final String DB_USER = "root";
    public static final String DB_PASS = "root";
    
    static {
        DB_URLS = new String[1];
        DB_URLS[0] = DB_URL;
        System.out.println("Initializing the DAO...");
        DAO.init();
    }
}
