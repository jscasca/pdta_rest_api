package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.User;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.UserServiceImplementation;

@Controller
@RequestMapping(value = "/api/users")
public class UserService {
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> findUsers(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUsers(start, limit);
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserById(id);
    }
    
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findFollowers(@PathVariable("id") final Long id,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return null;
    }
    
    @RequestMapping(value = "/{id:[0-9]+}/followees", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findFollowees(@PathVariable("id") final Long id,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return null;
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.POST)
    @ResponseBody
    public User followUser(@ModelAttribute CustomUserData userData, @PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return null;
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.DELETE)
    @ResponseBody
    public User unfollowUser(@ModelAttribute CustomUserData userData, @PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return null;
    }
    
    //Get user posdtas
    
    //get user wishlist
    
    //get user favorites
    
    //get user reading
    
    //get user read
    
    //get user ...
}
