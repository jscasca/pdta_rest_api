package com.pd.api.service;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.pd.api.entity.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.LoggedInWrapper;
import com.pd.api.entity.aux.StringWrapper;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.MyServiceImplementation;

@Controller
@RequestMapping(value = "/api/myservice")
@Secured({"ROLE_USER","ROLE_ADMIN"})
public class MyService extends AbstractService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public MyService() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public User findById(@ModelAttribute CustomUserData userData,
            final Principal principal,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //CustomUserData cu = loadUserFromSecurityContext();
        //String as = auth.toString();
        return MyServiceImplementation.getMe(userData.getUsername());
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/logIn")
    @ResponseBody
    public LoggedInWrapper logMeIn(@ModelAttribute CustomUserData userData) {
        return MyServiceImplementation.logIn(userData.getUsername());
    }

    //TODO: allow the limit t be passed as a parameter
    @RequestMapping(method = RequestMethod.GET, value="/libraryView")
    @ResponseBody
    public LibraryView getMyLibrary(@ModelAttribute final CustomUserData userData) {
        return MyServiceImplementation.getUserLibraryView(userData.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value="/groups")
    @ResponseBody
    public List<Club> getMyLibrary(@ModelAttribute final CustomUserData userData,
                                   @RequestParam(value="start", defaultValue="0") int first,
                                   @RequestParam(value="limit", defaultValue="25") int limit) {
        return MyServiceImplementation.getMyClubs(userData.getUsername(), first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/books")
    @ResponseBody
    public List<Book> getMyBooks(@ModelAttribute final CustomUserData userData,
                                 @RequestParam(value="filter", defaultValue = "") String filter,
                                 @RequestParam(value="start", defaultValue="0") int first,
                                 @RequestParam(value="limit", defaultValue="25") int limit) {
        return MyServiceImplementation.getMyBooks(userData.getUsername(), filter, first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/reads")
    @ResponseBody
    public List<Book> getMyReadings(@ModelAttribute final CustomUserData userData,
                                 @RequestParam(value="filter", defaultValue = "") String filter,
                                 @RequestParam(value="start", defaultValue="0") int first,
                                 @RequestParam(value="limit", defaultValue="50") int limit) {
        return MyServiceImplementation.getMyReadBooks(userData.getUsername(), filter, first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/wishlists")
    @ResponseBody
    public List<Book> getWishlisted(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="25") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getWishlisted(userData.getUsername(), first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/reading")
    @ResponseBody
    public List<Book> getReadings(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="25") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getReading(userData.getUsername(), first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/favorites")
    @ResponseBody
    public List<Book> getFavorites(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="25") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getFavorited(userData.getUsername(), first, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value="/posdtas")
    @ResponseBody
    public List<Posdta> getPosdtas(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="25") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getPosdtas(userData.getUsername(), first, limit);
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/recommendations")
    @ResponseBody
    public List<Book> getMyRecommendations(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="25") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getUserRecommendations(userData.getUsername(), first, limit);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/displayname")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDisplayName(@ModelAttribute final CustomUserData userData,
            @RequestBody final StringWrapper name) {
        MyServiceImplementation.updateMyDisplayName(userData.getUsername(), name.getValue());
    }
    
    /**
     * Ideally this method should use PUT.
     * Due to some Spring MVC issues POST had to be used instead
     */
    @RequestMapping(method = RequestMethod.POST, value = "/avatar")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateAvatar(@ModelAttribute final CustomUserData userData,
            @RequestBody final StringWrapper avatar) {
        MyServiceImplementation.updateMyAvatar(userData.getUsername(), avatar.getValue());
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    @ResponseBody
    public User getMe(@ModelAttribute final CustomUserData userData) {
        return MyServiceImplementation.getMe(userData.getUsername());
    }

}
