package com.nw.primefinder.controller;

import com.nw.primefinder.model.GetPrimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.nw.primefinder.service.PrimeFinderService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@Slf4j
public class PrimeFinderController {
    @Autowired
     PrimeFinderService primeFinderService;
     @RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    public String health(){
        return "prime finder is up";
    }

    @RequestMapping(value = "/prime/{initial}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(OK)
    @ResponseBody
    public GetPrimeResponse getPrimes(@PathVariable int initial){
        log.trace("started executing method getPrimes");

        GetPrimeResponse getPrimeResponse = primeFinderService.getPrimeReaponse(initial);
        log.trace("finished executing method getPrimes");
        return getPrimeResponse;
    }

}
