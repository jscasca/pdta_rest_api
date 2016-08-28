package com.pd.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.AdminServiceImplementation;

@Controller
@RequestMapping(value = "/api/admin")
public class AdminService {

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/reindex", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void reindex(@ModelAttribute final CustomUserData userData) {
        AdminServiceImplementation.reindexAll();
    }
}
