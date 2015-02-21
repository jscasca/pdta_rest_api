package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.service.impl.SearchServiceImplementation;

@Controller
@RequestMapping(value = "/api/search")
public class SearchService {

    @RequestMapping(value="/books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBooks(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            @RequestParam(value="query", defaultValue="") String query,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return SearchServiceImplementation.searchBooks(query, start, limit);
    }
}
