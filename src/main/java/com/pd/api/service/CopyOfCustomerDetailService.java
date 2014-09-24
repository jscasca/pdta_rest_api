package com.pd.api.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.CustomerDetails;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/reg/customer")
public class CopyOfCustomerDetailService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public CopyOfCustomerDetailService() {
        super();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CustomerDetails findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return new CustomerDetails(randomAlphabetic(6));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerDetails> findAll() {
        return Lists.newArrayList(new CustomerDetails(randomAlphabetic(6)));
    }

}
