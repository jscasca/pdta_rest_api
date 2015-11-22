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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.entity.aux.LoggedInWrapper;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.MyServiceImplementation;

@Controller
@RequestMapping(value = "/api/health")
public class HealthService extends AbstractService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public HealthService() {
        super();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getMyLibrary(@ModelAttribute final CustomUserData userData) {
        return "OK";
    }

}
