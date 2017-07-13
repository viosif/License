package com.fortech.controller;

import com.fortech.model.Client;
import com.fortech.model.License;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        clientRepository.save(client);
        return client;
    }

    @RequestMapping(path = "/deleteByEmail", method = RequestMethod.DELETE)
    public Client deleteClientByEmail(@RequestParam("email") String email) {
        Client client = clientRepository.findFirstByEmail(email);
        licenseRepository.delete(client.getLicense());
        clientRepository.deleteByEmail(email);
        return client;
    }

    @RequestMapping(path = "/findByEmail", method = RequestMethod.GET)
    public Iterable<Client> findByEmail(@RequestParam("email") String email) {
        return clientRepository.findByEmail(email);
    }

    @RequestMapping(path = "/getAllLicenseByEmail", method = RequestMethod.GET)
    public List<License> getAllLicenseByEmail(@RequestParam("email") String email) {
        return clientRepository.findFirstByEmail(email).getLicense();
    }

    @RequestMapping(path = "/addLicenseToClient", method = RequestMethod.POST)
    public Client addLicenseToClient(@RequestBody License license, @RequestParam("clientId") String clientId) {
        licenseRepository.save(license);
        Client client = clientRepository.findOne(Long.valueOf(clientId));
        List<License> licenses = client.getLicense();
        licenses.add(license);
        client.setLicense(licenses);
        clientRepository.save(client);
        return client;
    }

    @RequestMapping(path = "/deleteLicenseFromClient", method = RequestMethod.DELETE)
    public Client deleteLicenseFromClient(@RequestParam("clientId") String clientId,@RequestParam("licenseKey") String licenseKey) {

        Client client = clientRepository.findOne(Long.valueOf(clientId));
        List<License> licenses = client.getLicense();
        License toDelete=null;
        for(License license : licenses)
            if (license.getLicenseKey().equals(licenseKey))
                toDelete = license;

        licenses.remove(toDelete);
        client.setLicense(licenses);
        licenseRepository.deleteByLicenseKey(toDelete.getLicenseKey());
        clientRepository.save(client);
        return client;
    }



}