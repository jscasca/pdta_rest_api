package com.pd.api.service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.LoggedInWrapper;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.MyServiceImplementation;

@Controller
@RequestMapping(value = "/api/me")
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
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserData cu = loadUserFromSecurityContext();
        String as = auth.toString();
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
    
    @RequestMapping(method = RequestMethod.GET, value="/recommendations")
    @ResponseBody
    public List<Book> getMyRecommendations(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getUserRecommendations(userData.getUsername(), first, limit);
    }

}
