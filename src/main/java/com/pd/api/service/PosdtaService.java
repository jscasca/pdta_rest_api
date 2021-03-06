package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.PosdtaVoting;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.AuthorWrapper;
import com.pd.api.entity.aux.WorkWrapper;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.AuthorServiceImplementation;
import com.pd.api.service.impl.PosdtaServiceImplementation;

@Controller
@RequestMapping(value = "/api/posdtas")
public class PosdtaService {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Posdta> getPosdtas(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return PosdtaServiceImplementation.getPosdtas(start, limit);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/upvote", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void upvotePosdta(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long posdtaId) {
        PosdtaServiceImplementation.upvotePosdta(userData.getUsername(), posdtaId);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/downvote", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void downvotePosdta(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long posdtaId) {
        PosdtaServiceImplementation.downvotePosdta(userData.getUsername(), posdtaId);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/upvote", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeUpvote(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long posdtaId) {
        //TODO: design if implementation is reasonable
        //PosdtaServiceImplementation.removeUpvote(userData.getUsername(), posdtaId);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/downvote", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeDownvote(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long posdtaId) {
        //TODO
    }
}
