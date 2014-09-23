package com.pd.api.db;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ThreadEntityManagerFactory {

    protected static final String DEFAULT_CONNECTION_STRING = "jdbc:mysql://"
            + System.getProperty("JDBC_CONNECTION_STRING", "localhost")
            + "/posdta_" + "1_0" + "?useUnicode=true&amp;characterEncoding=UTF-8";
    
    protected static HashMap<String, EntityManagerFactory> emfMap = new HashMap<String, EntityManagerFactory>() {
        { put(DEFAULT_CONNECTION_STRING, null); };
    };
    
    protected HashMap<String, ThreadLocalEntityManager> tlemMap = new HashMap<String, ThreadLocalEntityManager>() {
        { put(DEFAULT_CONNECTION_STRING, new ThreadLocalEntityManager("posdta", DEFAULT_CONNECTION_STRING)); }
    };
    
    private EntityManager createEntityManager(String dburl, String persistenceContext) {
        return emfMap.get(dburl).createEntityManager();
    }
    
    public EntityManager getEntityManager() {
        return getEntityManager(DEFAULT_CONNECTION_STRING, "posdta");
    }
    
    public EntityManager getEntityManager(String dburl, String persistenceContext) {
        if (!tlemMap.containsKey(dburl))
            tlemMap.put(dburl, new ThreadLocalEntityManager(persistenceContext, dburl));
        ThreadLocalEntityManager tlem = tlemMap.get(dburl);
        EntityManager em = tlem.get();
        if (!em.isOpen()) {
            em = createEntityManager(dburl, persistenceContext);
            tlem.set(em);
        }
        return em;
    }
    
    /**
     * Class ThreadLocalEntityManager.
     */
    public class ThreadLocalEntityManager extends ThreadLocal<EntityManager> {
        private String persistenceContext;
        private String dburl;
        
        public ThreadLocalEntityManager(String persistenceCntxt, String url) {
            super();
            this.persistenceContext = persistenceCntxt;
            this.dburl = url;
        }
        
        @Override
        protected EntityManager initialValue() {
            if (emfMap.get(dburl) == null) {
                if (dburl != null) {
                    // override jdbc server
                    HashMap<String, String> map = new HashMap<String, String>();
                    String hibernateConnection = dburl;
                    map.put("hibernate.connection.url", hibernateConnection);
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceContext, map);
                    emfMap.put(dburl, emf);
                }
                else
                    emfMap.put(dburl, Persistence.createEntityManagerFactory(persistenceContext));
            }
            return createEntityManager(dburl, persistenceContext);
        }
    }
}
