package org.baeldung.web.tracing.controller;


import org.baeldung.web.dto.BaeldungItem;
import org.baeldung.web.dto.ItemDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TracedController {

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@RequestBody BaeldungItem baeldungItem) {
        return restTemplate.getForEntity("http://localhost:8089/item/" + baeldungItem.getItemId(), ItemDetails.class);
    }
}
