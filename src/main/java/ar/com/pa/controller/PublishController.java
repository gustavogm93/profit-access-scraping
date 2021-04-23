package ar.com.pa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pa.collections.country.CountryProp;
import ar.com.pa.collections.region.RegionProp;
import ar.com.pa.generics.Property;
import ar.com.pa.queue.SystemMessage;

@RestController
public class PublishController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody Property systemMessage) {
        try {
        	RegionProp reg = new RegionProp("12","America");
        	CountryProp cou = new CountryProp("230","Argentina");
        	SystemMessage sa = new SystemMessage(cou, reg);
            jmsTemplate.convertAndSend("region", sa);

            return new ResponseEntity<>("Sent.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}