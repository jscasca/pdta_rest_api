package com.pd.api.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;
import com.pd.api.entity.CustomerDetails;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.PublicServiceImplementation;
import com.pd.api.exception.GeneralException;

/**
 * Public services are offered without authentication
 * This services are exposed to register, recover passwords or find available names
 * 
 * @author tin
 *
 */
@Controller
@RequestMapping(value = "/public")
public class PublicService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User registerMember(@RequestBody MemberRegistration registration) {
        return PublicServiceImplementation.registerMember(registration);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CustomerDetails findById(@ModelAttribute CustomUserData userData, @PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return new CustomerDetails(userData.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerDetails> findAll() {
        return Lists.newArrayList(new CustomerDetails(randomAlphabetic(6)));
    }
}
