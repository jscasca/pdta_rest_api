package com.pd.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.User;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.MyServiceImplementation;

@Controller
@RequestMapping(value = "/api/me")
@Secured({"ROLE_USER","ROLE_ADMIN"})
public class MyService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public MyService() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public User findById(@ModelAttribute CustomUserData userData,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return MyServiceImplementation.getMe(userData.getUsername());
    }

}
