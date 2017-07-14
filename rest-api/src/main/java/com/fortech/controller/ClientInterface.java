package com.fortech.controller;

import com.fortech.dto.ClientDTO;
import com.fortech.dto.LicenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by iosifvarga on 14.07.2017.
 */
public interface ClientInterface {

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<ClientDTO> getAllClient();

    @Transactional(readOnly = true)
    @RequestMapping(path = "/listPage", method = RequestMethod.GET)
    public Page<ClientDTO> listPage(Pageable pageable);

    @RequestMapping(value = "/createClientsMock", method = RequestMethod.GET)
    public String createClientsMock();

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO);

    @RequestMapping(path = "/deleteByEmail", method = RequestMethod.DELETE)
    public ClientDTO deleteClientByEmail(@RequestParam("email") String email);

    @RequestMapping(path = "/findByEmail", method = RequestMethod.GET)
    public Iterable<ClientDTO> findByEmail(@RequestParam("email") String email);

    @RequestMapping(path = "/findByEmailPage", method = RequestMethod.GET)
    public Page<ClientDTO> findByEmailPage(Pageable pageable, @RequestParam("email") String email);

    @RequestMapping(path = "/getAllLicenseByEmail", method = RequestMethod.GET)
    public List<LicenseDTO> getAllLicenseByEmail(@RequestParam("email") String email);
}
