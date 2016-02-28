package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Event;
import com.pd.api.service.impl.EventServiceImplementation;
import com.pd.api.service.impl.SearchServiceImplementation;

@Controller
@RequestMapping(value = "/api/events")
public class EventService {

    @RequestMapping(value="/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> findBooks(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return EventServiceImplementation.getAllEvents(start, limit);
    }
}
