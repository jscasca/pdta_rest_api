package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.BookWrapper;
import com.pd.api.service.impl.WorkServiceImplementation;

@Controller
@RequestMapping(value = "/api/works")
public class WorkService {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Work> findWorks(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return WorkServiceImplementation.getWorks(start, limit);
    }
    
    @RequestMapping(value="/{id:[0-9]+}")
    @ResponseBody
    public Work findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return WorkServiceImplementation.getWorkById(id);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/{id:[0-9]+}/books", method = RequestMethod.POST)
    @ResponseBody
    public Book createBook(@PathVariable("id") final Long id,
            BookWrapper bookWrapper,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return WorkServiceImplementation.createBook(id, bookWrapper);
    }
    
    @RequestMapping(value="/{id:[0-9]+}/books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> workBooks(@PathVariable("id") final Long id,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="0") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return WorkServiceImplementation.getBooksByWork(id, start, limit);
    }
}
