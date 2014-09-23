package com.pd.api.db;

import com.pd.api.PSDT;

public class PosdtaThreadEntityManagerFactory extends ThreadEntityManagerFactory {

    private static final PosdtaThreadEntityManagerFactory INSTANCE = new PosdtaThreadEntityManagerFactory();
    
    private PosdtaThreadEntityManagerFactory() {
        for(String dbUrl : PSDT.DB_URLS) {
            ThreadLocalEntityManager tlem = new ThreadLocalEntityManager("posdta", dbUrl);
            tlemMap.put(dbUrl, tlem);
            tlem.get();
        }
    }
    
    public static PosdtaThreadEntityManagerFactory getInstance() {
        return INSTANCE;
    }
}
