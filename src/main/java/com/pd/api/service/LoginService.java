package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Event;
import com.pd.api.entity.aux.SocialLoginForm;
import com.pd.api.security.AuthenticatedUserToken;
import com.pd.api.service.impl.EventServiceImplementation;
import com.pd.api.service.impl.LoginServiceImplementation;

@Controller
@RequestMapping(value = "/login")
public class LoginService {

    @RequestMapping(value="/{providerId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Authentication authenticateSocialMediaLogin(@RequestBody SocialLoginForm login,
            @PathVariable("providerId") final String provider) {
        return LoginServiceImplementation.loginBySocialMedia(provider, login);
    }
    
    @RequestMapping(value="/{providerId}/isUserRegistered", method = RequestMethod.GET)
    @ResponseBody
    public boolean userIsRegistered(@RequestParam(value="userId") String userId,
            @PathVariable("providerId") final String provider) {
        return LoginServiceImplementation.isUserRegistered(provider, userId);
    }
}
