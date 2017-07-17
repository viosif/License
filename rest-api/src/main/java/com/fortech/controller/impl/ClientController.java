package com.fortech.controller.impl;

import com.fortech.controller.ClientInterface;
import com.fortech.keyGenerator.generateKey;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import com.fortech.dto.ClientDTO;
import com.fortech.dto.LicenseDTO;
import com.fortech.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@CrossOrigin
@RestController    // This means that this class is a Controller
@RequestMapping(value = "/client") // This means URL's start with /demo (after Application path)
public class ClientController implements ClientInterface {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    LicenseRepository licenseRepository;


    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<ClientDTO> getAllClient() {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        Iterable<Client> clients = clientRepository.findAll();
        clients.forEach(client -> {
            clientDTOS.add(client.toDto());
        });
        return clientDTOS;
    }


    @Transactional(readOnly = true)
    @RequestMapping(path = "/listPage", method = RequestMethod.GET)
    public Page<ClientDTO> listPage(Pageable pageable) {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        Page<Client> clientPage = clientRepository.findAll(pageable);
        clientPage.getContent().forEach(client -> {
            clientDTOS.add(client.toDto());
        });

        return new PageImpl<ClientDTO>(clientDTOS, pageable, clientPage.getTotalElements());
    }

    @RequestMapping(value = "/createClientsMock", method = RequestMethod.GET)
    public String createClientsMock() {
        Client client;
        License license;
        for (int i = 0; i < 100; i++) {
            client = new Client();
            client.setName("varga" + i);
            client.setSurname("iosif" + i);
            client.setAge(i);
            client.setEmail("email" + i + "@email.com");

            license = new License();
            license.setLicenseType(LicenseType.TRIAL);
            license.setEndDate(new Date(1500015104410L));
            license.setLicenseKey(generateKey.generateLicenseKey());
            license.setKeyStatus(KeyStatus.KEY_GOOD);
            licenseRepository.save(license);
            client.setLicense(Arrays.asList(license));
            clientRepository.save(client);
        }

        return "ok";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        clientRepository.save(clientDTO.toEntity());
        return clientDTO;
    }

    @RequestMapping(path = "/deleteByEmail", method = RequestMethod.DELETE)
    public ClientDTO deleteClientByEmail(@RequestParam("email") String email) {
        Client client = clientRepository.findFirstByEmail(email);
        licenseRepository.delete(client.getLicense());
        clientRepository.deleteByEmail(email);
        return client.toDto();
    }

    @RequestMapping(path = "/findByEmail", method = RequestMethod.GET)
    public Iterable<ClientDTO> findByEmail(@RequestParam("email") String email) {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        Iterable<Client> clients = clientRepository.findByEmail(email);
        clients.forEach(client -> {
            clientDTOS.add(client.toDto());
        });
        return clientDTOS;
    }

    @RequestMapping(path = "/findByEmailPage", method = RequestMethod.GET)
    public Page<ClientDTO> findByEmailPage(Pageable pageable, @RequestParam("email") String email) {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        Page<Client> clientPage = clientRepository.findByEmailLike(pageable, email);
        clientPage.getContent().forEach(client -> {
            clientDTOS.add(client.toDto());
        });

        return new PageImpl<ClientDTO>(clientDTOS, pageable, clientPage.getTotalElements());
    }

    @RequestMapping(path = "/getAllLicenseByEmail", method = RequestMethod.GET)
    public List<LicenseDTO> getAllLicenseByEmail(@RequestParam("email") String email) {
        List<LicenseDTO> licenseDTOS = new ArrayList<>();
        List<License> licenses = clientRepository.findFirstByEmail(email).getLicense();
        licenses.forEach(license -> {
            licenseDTOS.add(license.toDto());
        });
        return licenseDTOS;
    }


    //just for learning
    /*
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
    public Client deleteLicenseFromClient(@RequestParam("clientId") String clientId, @RequestParam("licenseKey") String licenseKey) {
        Client client = clientRepository.findOne(Long.valueOf(clientId));
        List<License> licenses = client.getLicense();
        License toDelete = null;
        for (License license : licenses)
            if (license.getLicenseKey().equals(licenseKey))
                toDelete = license;
        licenses.remove(toDelete);
        client.setLicense(licenses);
        licenseRepository.deleteByLicenseKey(toDelete.getLicenseKey());
        clientRepository.save(client);
        return client;
    }*/


}