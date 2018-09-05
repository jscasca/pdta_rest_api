package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.pd.api.entity.aux.UserProfile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.UserInfo;
import com.pd.api.entity.aux.UserToUser;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.UserServiceImplementation;

@Controller
@RequestMapping(value = "/api/users")
public class UserService {
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> findUsers(@RequestParam(value="start", defaultValue="0", required = false) int start,
            @RequestParam(value="limit", defaultValue="10", required = false) int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUsers(start, limit);
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserById(id);
    }

    @RequestMapping(value = "/{id:[0-9]+}/info", method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserInfo(id);
    }

    @RequestMapping(value = "/{id:[0-9]+}/library", method = RequestMethod.GET)
    @ResponseBody
    public UserProfile getUserProfile(@PathVariable("id") final Long id,
                                      final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserProfile(id);
    }

    @RequestMapping(value = "/{id:[0-9]+}/libraryView", method = RequestMethod.GET)
    @ResponseBody
    public LibraryView getUserLibraryView(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserLibraryView(id);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value= "/{id:[0-9]+}/interactions", method = RequestMethod.GET)
    @ResponseBody
    public UserToUser getUserInteractions(@PathVariable("id") final Long userId,
            @ModelAttribute final CustomUserData userData) {
        return UserServiceImplementation.getUserInteractions(userData.getUsername(), userId);
    }
    
    /**
     *
     * @pathVariable id
     * @param first
     * @param limit
     * @param uriBuilder
     * @param response
     * @return A list of users who are following the selected user
     */
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findFollowers(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserFollowers(userId, first, limit);
    }
    
    /**
     * 
     * @pathVariable id
     * @param first
     * @param limit
     * @param uriBuilder
     * @param response
     * @return A list of users that the selected user follows
     */
    @RequestMapping(value = "/{id:[0-9]+}/followees", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findFollowees(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUsersFollowing(userId, first, limit);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.POST)
    @ResponseBody
    public User followUser(@ModelAttribute CustomUserData userData, 
            @PathVariable("id") final Long userId,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.followUser(userData.getUsername(), userId);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id:[0-9]+}/followers", method = RequestMethod.DELETE)
    @ResponseBody
    public User unfollowUser(@ModelAttribute CustomUserData userData, 
            @PathVariable("id") final Long userId,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.unfollowUser(userData.getUsername(), userId);
    }
    
    //Get user posdtas
    @RequestMapping(value = "/{id:[0-9]+}/posdtas", method = RequestMethod.GET)
    @ResponseBody
    public List<Posdta> getPosdtas(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserPosdtas(userId, first, limit);
    }
    
    //get user wishlist
    @RequestMapping(value = "/{id:[0-9]+}/wishlists", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getWishlisted(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserWishlisted(userId, first, limit);
    }
    
    //get user favorites
    @RequestMapping(value = "/{id:[0-9]+}/favorites", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getFavorites(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserFavorites(userId, first, limit);
    }
    
    //get user reading
    @RequestMapping(value = "/{id:[0-9]+}/readings", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getReading(@PathVariable("id") final Long userId,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return UserServiceImplementation.getUserReading(userId, first, limit);
    }
}
