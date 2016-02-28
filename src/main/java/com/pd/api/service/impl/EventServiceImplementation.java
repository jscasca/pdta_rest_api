package com.pd.api.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Event;

public class EventServiceImplementation {

    public static List<Event> getEventsFrom(int start, int limit, Timestamp from) {
        return DAO.getAll(Event.class, start, limit);
    }
    
    public static List<Event> getAllEvents(int start, int limit) {
        return DAO.getAll(Event.class, "", "order by eventDate desc", start, limit);
    }
}
