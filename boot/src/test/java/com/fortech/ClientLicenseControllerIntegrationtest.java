package com.fortech;

import com.fortech.services.GenerateKey;
import com.fortech.model.Client;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *
 * Created by iosifvarga on 07.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.JVM)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientLicenseControllerIntegrationtest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    protected FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ClientRepository clientRepository;

    private Client client1;
    private Client client2;
    private String name1 = "name1";
    private String surname1 = "surname1";
    private int age1 = 10;
    private String email1 = "email1@email.com";
    private String name2 = "name2";
    private String surname2 = "surname2";
    private int age2 = 20;
    private String email2 = "email2@email.com";

    @Autowired
    private LicenseRepository licenseRepository;

    private License license1;
    private LicenseType licenseType1 = LicenseType.LIFETIME;
    private Date endDate1 = new Date();
    private KeyStatus keyStatus1 = KeyStatus.KEY_GOOD;
    private License license2;
    private LicenseType licenseType2 = LicenseType.TRIAL;
    private Date endDate2 = new Date();
    private KeyStatus keyStatus2 = KeyStatus.KEY_BLACKLISTED;
    private License license3;
    private LicenseType licenseType3 = LicenseType.SINGLE_VERSION;
    private Date endDate3 = new Date();
    private KeyStatus keyStatus3 = KeyStatus.KEY_EXPIRED;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Before
    public void initRepository() {
        this.licenseRepository.deleteAll();
        this.clientRepository.deleteAll();

        this.client1 = new Client();
        this.client1.setName(name1);
        this.client1.setSurname(surname1);
        this.client1.setAge(age1);
        this.client1.setEmail(email1);

        this.client2 = new Client();
        this.client2.setName(name2);
        this.client2.setSurname(surname2);
        this.client2.setAge(age2);
        this.client2.setEmail(email2);

        this.license1 = new License();
        this.license1.setLicenseType(licenseType1);
        this.license1.setEndDate(endDate1);
        this.license1.setLicenseKey(GenerateKey.generateLicenseKey());
        this.license1.setKeyStatus(keyStatus1);
        this.licenseRepository.save(this.license1);

        this.license2 = new License();
        this.license2.setLicenseType(licenseType2);
        this.license2.setEndDate(endDate2);
        this.license2.setLicenseKey(GenerateKey.generateLicenseKey());
        this.license2.setKeyStatus(keyStatus2);
        this.licenseRepository.save(this.license2);

        this.license3 = new License();
        this.license3.setLicenseType(licenseType3);
        this.license3.setEndDate(endDate3);
        this.license3.setLicenseKey(GenerateKey.generateLicenseKey());
        this.license3.setKeyStatus(keyStatus3);
        this.licenseRepository.save(this.license3);

        List<License> licenses1 = new ArrayList<>();
        licenses1.add(this.license1);
        licenses1.add(this.license2);

        List<License> licenses2 = new ArrayList<>();
        licenses2.add(this.license3);

        this.client1.setLicense(licenses1);
        this.client2.setLicense(licenses2);
        this.clientRepository.save(this.client1);
        this.clientRepository.save(this.client2);
    }

    @After
    public void clearPostGres() {
        this.licenseRepository.deleteAll();
        this.clientRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void getAllClient() throws Exception {
        this.mockMvc.perform(get("/client/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is(name1)))
                .andExpect(jsonPath("$[0].surname", Matchers.is(surname1)))
                .andExpect(jsonPath("$[0].age", Matchers.is(age1)))
                .andExpect(jsonPath("$[0].email", Matchers.is(email1)))
                .andExpect(jsonPath("$[0].license[0].licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$[0].license[0].endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$[0].license[0].licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$[0].license[0].keyStatus", Matchers.is(keyStatus1.toString())))
                .andExpect(jsonPath("$[0].license[1].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$[0].license[1].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$[0].license[1].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$[0].license[1].keyStatus", Matchers.is(keyStatus2.toString())))
                .andExpect(jsonPath("$[1].name", Matchers.is(name2)))
                .andExpect(jsonPath("$[1].surname", Matchers.is(surname2)))
                .andExpect(jsonPath("$[1].age", Matchers.is(age2)))
                .andExpect(jsonPath("$[1].email", Matchers.is(email2)))
                .andExpect(jsonPath("$[1].license[0].licenseType", Matchers.is(licenseType3.toString())))
                .andExpect(jsonPath("$[1].license[0].endDate", Matchers.is(endDate3.getTime())))
                .andExpect(jsonPath("$[1].license[0].licenseKey", Matchers.is(license3.getLicenseKey())))
                .andExpect(jsonPath("$[1].license[0].keyStatus", Matchers.is(keyStatus3.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void createClient() throws Exception {
        String someDate = "2017-07-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(someDate);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "name1");
        jsonObject.put("surname", "surname1");
        jsonObject.put("age", "87");
        jsonObject.put("email", "email3@email.com");

        this.mockMvc.perform(post("/client/")
                .contentType(
                        MediaType.APPLICATION_JSON).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("name1")))
                .andExpect(jsonPath("$.surname", Matchers.is("surname1")))
                .andExpect(jsonPath("$.age", Matchers.is(87)))
                .andExpect(jsonPath("$.email", Matchers.is("email3@email.com")))
                .andDo(print()).andReturn();
    }

    @Test
    //@WithMockUser(username = "admin", roles = {"ANONYMOUS"})
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteByEmail() throws Exception {
        this.mockMvc.perform(delete("/client/deleteByEmail")
                .param("email", client1.getEmail())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(client1.getName())))
                .andExpect(jsonPath("$.surname", Matchers.is(client1.getSurname())))
                .andExpect(jsonPath("$.age", Matchers.is(client1.getAge())))
                .andExpect(jsonPath("$.email", Matchers.is(client1.getEmail())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findByEmail() throws Exception {
        this.mockMvc.perform(get("/client/findByEmail")
                .param("email", email1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Matchers.is(name1)))
                .andExpect(jsonPath("$[0].surname", Matchers.is(surname1)))
                .andExpect(jsonPath("$[0].age", Matchers.is(age1)))
                .andExpect(jsonPath("$[0].email", Matchers.is(email1)))
                .andExpect(jsonPath("$[0].license[0].licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$[0].license[0].endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$[0].license[0].licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$[0].license[0].keyStatus", Matchers.is(keyStatus1.toString())))
                .andExpect(jsonPath("$[0].license[1].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$[0].license[1].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$[0].license[1].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$[0].license[1].keyStatus", Matchers.is(keyStatus2.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void getAllLicenseByEmail() throws Exception {
        this.mockMvc.perform(get("/client/getAllLicenseByEmail")
                .param("email", email1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$[0].endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$[0].licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$[0].keyStatus", Matchers.is(keyStatus1.toString())))
                .andExpect(jsonPath("$[1].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$[1].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$[1].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$[1].keyStatus", Matchers.is(keyStatus2.toString())))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }


    /*@Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void createClientsMock() throws Exception {
        licenseRepository.deleteAll();
        clientRepository.deleteAll();
        this.mockMvc.perform(get("/client/createClientsMock")
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }*/


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void listPage() throws Exception {

        licenseRepository.deleteAll();
        clientRepository.deleteAll();

        this.mockMvc.perform(get("/client/createClientsMock")
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        this.mockMvc.perform(get("/client/listPage")
                .param("page",String.valueOf(2))
                .param("size",String.valueOf(10))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name", Matchers.is("varga20")))
                .andExpect(jsonPath("$.content[0].surname", Matchers.is("iosif20")))
                .andExpect(jsonPath("$.content[0].age", Matchers.is(20)))
                .andExpect(jsonPath("$.content[0].email", Matchers.is("email20@email.com")))
                .andExpect(jsonPath("$.content[0].license[0].licenseType", Matchers.is("TRIAL")))
                .andExpect(jsonPath("$.content[0].license[0].endDate", Matchers.is(1500015104410L)))
                .andExpect(jsonPath("$.content[0].license[0].keyStatus", Matchers.is("KEY_GOOD")))

                .andExpect(jsonPath("$.last", Matchers.is(Boolean.FALSE)))
                .andExpect(jsonPath("$.totalElements", Matchers.is(100)))
                .andExpect(jsonPath("$.totalPages", Matchers.is(10)))
                .andExpect(jsonPath("$.sort", nullValue()))
                .andExpect(jsonPath("$.numberOfElements", Matchers.is(10)))
                .andExpect(jsonPath("$.first", Matchers.is(Boolean.FALSE)))
                .andExpect(jsonPath("$.size", Matchers.is(10)))
                .andExpect(jsonPath("$.number", Matchers.is(2)))

                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findByEmailPage() throws Exception {

        licenseRepository.deleteAll();
        clientRepository.deleteAll();

        this.mockMvc.perform(get("/client/createClientsMock")
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        this.mockMvc.perform(get("/client/findByEmailPage")
                .param("page",String.valueOf(0))
                .param("size",String.valueOf(20))
                .param("email",String.valueOf("email1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name", Matchers.is("varga1")))
                .andExpect(jsonPath("$.content[0].surname", Matchers.is("iosif1")))
                .andExpect(jsonPath("$.content[0].age", Matchers.is(1)))
                .andExpect(jsonPath("$.content[0].email", Matchers.is("email1@email.com")))
                .andExpect(jsonPath("$.content[0].license[0].licenseType", Matchers.is("TRIAL")))
                .andExpect(jsonPath("$.content[0].license[0].endDate", Matchers.is(1500015104410L)))
                .andExpect(jsonPath("$.content[0].license[0].keyStatus", Matchers.is("KEY_GOOD")))

                .andExpect(jsonPath("$.last", Matchers.is(Boolean.TRUE)))
                .andExpect(jsonPath("$.totalElements", Matchers.is(11)))
                .andExpect(jsonPath("$.totalPages", Matchers.is(1)))
                .andExpect(jsonPath("$.sort", nullValue()))
                .andExpect(jsonPath("$.numberOfElements", Matchers.is(11)))
                .andExpect(jsonPath("$.first", Matchers.is(Boolean.TRUE)))
                .andExpect(jsonPath("$.size", Matchers.is(20)))
                .andExpect(jsonPath("$.number", Matchers.is(0)))

                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }


    /*
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void addLicenseToClient() throws Exception {
        LicenseType licenseType4 = LicenseType.LIFETIME;
        Date startDate4 = new Date();
        Date endDate4 = new Date();
        String licenseKey4 = GenerateKey.generateLicenseKey();
        KeyStatus keyStatus4 = KeyStatus.KEY_PHONY;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("licenseType", licenseType4);
        jsonObject.put("endDate", endDate4.getTime());
        jsonObject.put("licenseKey", licenseKey4);
        jsonObject.put("keyStatus", keyStatus4);
        List<Client> allClients = (List<Client>) clientRepository.findAll();
        Client client = allClients.get(0);
        this.mockMvc.perform(post("/client/addLicenseToClient")
                .param("clientId", String.valueOf(client.getId()))
                .contentType(
                        MediaType.APPLICATION_JSON).content(jsonObject.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name1)))
                .andExpect(jsonPath("$.surname", Matchers.is(surname1)))
                .andExpect(jsonPath("$.age", Matchers.is(age1)))
                .andExpect(jsonPath("$.email", Matchers.is(email1)))
                .andExpect(jsonPath("$.license[0].licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.license[0].endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.license[0].licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$.license[0].keyStatus", Matchers.is(keyStatus1.toString())))
                .andExpect(jsonPath("$.license[1].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$.license[1].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$.license[1].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$.license[1].keyStatus", Matchers.is(keyStatus2.toString())))
                .andExpect(jsonPath("$.license[2].licenseType", Matchers.is(licenseType4.toString())))
                .andExpect(jsonPath("$.license[2].endDate", Matchers.is(endDate4.getTime())))
                .andExpect(jsonPath("$.license[2].licenseKey", Matchers.is(licenseKey4)))
                .andExpect(jsonPath("$.license[2].keyStatus", Matchers.is(keyStatus4.toString())))
                .andDo(print()).andReturn();
    }
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void deleteLicenseFromClient() throws Exception {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        Client client = clients.get(0);
        this.mockMvc.perform(delete("/client/deleteLicenseFromClient")
                .param("clientId", String.valueOf(client.getId()))
                .param("licenseKey", this.license1.getLicenseKey())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name1)))
                .andExpect(jsonPath("$.surname", Matchers.is(surname1)))
                .andExpect(jsonPath("$.age", Matchers.is(age1)))
                .andExpect(jsonPath("$.email", Matchers.is(email1)))
                .andExpect(jsonPath("$.license[0].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$.license[0].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$.license[0].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$.license[0].keyStatus", Matchers.is(keyStatus2.toString())))
               .andDo(print()).andReturn();
    }*/
}