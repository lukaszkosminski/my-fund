package com.myfund.controller;

import com.myfund.model.ApplicationDetails;
import com.myfund.model.DTO.ApplicationDetailsDTO;
import com.myfund.service.ApplicationDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/")
public class VersionController {

    private final ApplicationDetailsService applicationDetailsService;

    public VersionController(ApplicationDetailsService applicationDetailsService) {
        this.applicationDetailsService = applicationDetailsService;
    }

    @GetMapping("/version")
    public ResponseEntity<ApplicationDetailsDTO> getVersion() throws URISyntaxException {
        return new ResponseEntity<>(applicationDetailsService.getVersion(), HttpStatus.OK);
    }
}
