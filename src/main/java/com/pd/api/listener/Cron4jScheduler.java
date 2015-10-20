package com.pd.api.listener;

import java.io.IOException;

import it.sauronsoftware.cron4j.Scheduler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pd.api.util.LuceneIndexer;

public class Cron4jScheduler implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        // 1. Retrieves the scheduler from the context
        Scheduler scheduler = (Scheduler) context.getAttribute("cron4j.scheduler");
        // 2. Remove the schedule from the context
        context.removeAttribute("cron4j.scheduler");
        // 3. Stop the scheduler
        scheduler.stop();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        // 1. Create the scheduler
        Scheduler scheduler = new Scheduler();
        // 2.a If needed, register a custom task collector
        //TaskCollector collector = new TaskCollector()/*or MyTaskCollector()*/
        //scheduler.addTaskCollector(collector);
        // 2.b Create a simple runnable task @@ */5 * * * * @@
        scheduler.schedule("* * * * *", new Runnable() {
            public void run() {
                System.out.println("Runing the indexer");
                //Run the indexing
                long startTime = System.nanoTime();
                /*try {
                    LuceneIndexer indexer = LuceneIndexer.getInstance();
                    indexer.index();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                long endTime = System.nanoTime();
                System.out.println("Indexing took [" + (endTime - startTime)/1000000 + "] miliseconds");
            }
        });
        // 3. Start the scheduler
        scheduler.start();
        // 4. Register the scheduler
        context.setAttribute("cron4j.scheduler", scheduler);
    }

}
