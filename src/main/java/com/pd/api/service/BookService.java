package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.service.impl.BookServiceImplementation;

@Controller
@RequestMapping(value = "/api/books")
public class BookService {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBooks(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return BookServiceImplementation.getBooks(start, limit);
    }
    
    @RequestMapping(value="/{id:[0-9]+}")
    @ResponseBody
    public Book findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return BookServiceImplementation.getBookById(id);
    }
    
    //favorite
    
    //wishlist
    
    //start reading
    
    //finihs reading
    
    //get posdtas
    
    //get users favorited
}
