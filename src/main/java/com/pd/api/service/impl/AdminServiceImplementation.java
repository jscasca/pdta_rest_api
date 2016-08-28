package com.pd.api.service.impl;

import com.pd.api.db.indexer.LuceneCronIndexer;
import com.pd.api.entity.Book;

public class AdminServiceImplementation {

    public static void recalculateVotes(int start, int limit) {
        //TODO: implement
    }
    
    public static void recalculateBookRating(Book book) {
        //TODO: implement
    }
    
    public static void reindexAll() {
        LuceneCronIndexer indexer = new LuceneCronIndexer();
        indexer.reindexAll();
    }
}
