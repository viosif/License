package com.fortech.controller;

import com.fortech.model.Client;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@CrossOrigin
@RestController    // This means that this class is a Controller
@RequestMapping(value = "/client") // This means URL's start with /demo (after Application path)
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    LicenseRepository licenseRepository;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Client createClient(@RequestBody Client client) {
        client.setCreated(new Date());
        clientRepository.save(client);
        return client;
    }


    @RequestMapping(path = "/deleteByEmail", method = RequestMethod.DELETE)
    public Client deleteClientByEmail(@RequestParam("email") String email) {
        Client client = clientRepository.findFirstByEmail(email);
        licenseRepository.deleteByClient(client);
        clientRepository.deleteByEmail(email);

        //v2
        //Iterable<License> licenses = licenseRepository.findByClient(clientRepository.findOne(id));
        //for (License license : licenses) {
        //    licenseRepository.delete(license.getId());
        //}
        //clientRepository.delete(id);

        return client;
    }

    @RequestMapping(path = "/findByEmail", method = RequestMethod.GET)
    public Iterable<Client> findByEmail(@RequestParam("email") String email) {
        return clientRepository.findByEmail(email);
    }

}