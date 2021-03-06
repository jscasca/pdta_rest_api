package com.pd.api.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pd.api.db.indexer.LuceneCronIndexer;

public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
        System.out.println("Starting Context Load Listener: Reindexing the posdta library");
        //Check that the file exists or create it if it does not
        File luceneDir = new File(LuceneCronIndexer.INDEX_DIR);
        if(!luceneDir.exists()) {
            //create the path
            luceneDir.mkdirs();
        }
        //LuceneIndexer.getInstance();
        long startTime = System.nanoTime();
        LuceneCronIndexer indexer = new LuceneCronIndexer();
        indexer.reindexAll();
        long endTime = System.nanoTime();
        System.out.println("Indexing took [" + (endTime - startTime)/1000000 + "] miliseconds");
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
