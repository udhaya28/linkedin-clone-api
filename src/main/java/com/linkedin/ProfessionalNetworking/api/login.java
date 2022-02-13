package com.linkedin.ProfessionalNetworking.api;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.linkedin.ProfessionalNetworking.dto.Login;
import com.linkedin.ProfessionalNetworking.model.LoginUser;
import com.linkedin.ProfessionalNetworking.response.ApiResponse;
import com.linkedin.ProfessionalNetworking.service.LoginService;
import com.linkedin.ProfessionalNetworking.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.linkedin.ProfessionalNetworking.response.ApiResponse;
import com.linkedin.ProfessionalNetworking.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class login {

    private static final Logger log = LoggerFactory.getLogger(login.class);


    @Autowired
    LoginService loginService;

    /**
     *
     * @param login
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Login login) throws JsonProcessingException {
        log.info("Login into Linkedin Professional Networking [{}]", login);
        ApiResponse apiResponse = new ApiResponse();

        if (login.getUserId() != null && login.getPassword() != null) {
            List<LoginUser> lstLoginUsr = loginService.checkUserLogin(login.getUserId(), login.getPassword());
            if (!lstLoginUsr.isEmpty()) {
                apiResponse.setStatus(HttpStatus.OK.toString());
                apiResponse.setMessage("Success");
            } else {
                apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
                apiResponse.setMessage(Constants.INVALID_USERNAME_PASSWORD);
            }

        } else {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            apiResponse.setMessage(Constants.INVALID_USERNAME_PASSWORD);
        }
        return ResponseEntity.ok().body(apiResponse);
    }


}
