package com.pd.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pd.api.util.LuceneIndexer;

public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
        System.out.println("Starting Context Load Listener");
        LuceneIndexer.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}
