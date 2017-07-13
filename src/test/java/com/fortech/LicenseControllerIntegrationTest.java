package com.fortech;

import com.fortech.model.Client;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import com.fortech.utils.Utils;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.JVM)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LicenseControllerIntegrationTest {

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
    private Date created1 = new Date();
    private String name2 = "name2";
    private String surname2 = "surname2";
    private int age2 = 20;
    private String email2 = "email2@email.com";
    private Date created2 = new Date();

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
        initRepository();
    }

    void initRepository(){
        this.licenseRepository.deleteAll();
        this.clientRepository.deleteAll();

        this.client1 = new Client();
        this.client1.setName(name1);
        this.client1.setSurname(surname1);
        this.client1.setAge(age1);
        this.client1.setEmail(email1);
        this.client1.setCreated(created1);
        this.clientRepository.save(this.client1);

        this.client2 = new Client();
        this.client2.setName(name2);
        this.client2.setSurname(surname2);
        this.client2.setAge(age2);
        this.client2.setEmail(email2);
        this.client2.setCreated(created2);
        this.clientRepository.save(this.client2);

        this.license1 = new License();
        this.license1.setLicenseType(licenseType1);
        this.license1.setEndDate(endDate1);
        this.license1.setLicenseKey(Utils.generateLicenseKey());
        this.license1.setKeyStatus(keyStatus1);
        this.licenseRepository.save(this.license1);

        this.license2 = new License();
        this.license2.setLicenseType(licenseType2);
        this.license2.setEndDate(endDate2);
        this.license2.setLicenseKey(Utils.generateLicenseKey());
        this.license2.setKeyStatus(keyStatus2);
        this.licenseRepository.save(this.license2);

        this.license3 = new License();
        this.license3.setLicenseType(licenseType3);
        this.license3.setEndDate(endDate3);
        this.license3.setLicenseKey(Utils.generateLicenseKey());
        this.license3.setKeyStatus(keyStatus3);
        this.licenseRepository.save(this.license3);

        List<License> licenses1 = new ArrayList<>();
        licenses1.add(license1);
        licenses1.add(license2);

        List<License> licenses2 = new ArrayList<>();
        licenses2.add(license3);

        client1.setLicense(licenses1);
        client2.setLicense(licenses2);
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
    public void getAllLicense() throws Exception {
        this.mockMvc.perform(get("/license/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$[0].endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$[0].licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$[0].keyStatus", Matchers.is(keyStatus1.toString())))
                .andExpect(jsonPath("$[1].licenseType", Matchers.is(licenseType2.toString())))
                .andExpect(jsonPath("$[1].endDate", Matchers.is(endDate2.getTime())))
                .andExpect(jsonPath("$[1].licenseKey", Matchers.is(license2.getLicenseKey())))
                .andExpect(jsonPath("$[1].keyStatus", Matchers.is(keyStatus2.toString())))
                .andExpect(jsonPath("$[2].licenseType", Matchers.is(licenseType3.toString())))
                .andExpect(jsonPath("$[2].endDate", Matchers.is(endDate3.getTime())))
                .andExpect(jsonPath("$[2].licenseKey", Matchers.is(license3.getLicenseKey())))
                .andExpect(jsonPath("$[2].keyStatus", Matchers.is(keyStatus3.toString())))
                .andDo(print()).andReturn();
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void createLicense() throws Exception {
        String startDate = "2017-07-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(startDate);
        List<Client> all = (List<Client>) clientRepository.findAll();
        Client client = all.get(0);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("licenseType","TRIAL");
        jsonObject.put("endDate",date1.getTime());
        jsonObject.put("keyStatus","KEY_INVALID");

        this.mockMvc.perform(post("/license/")
                .contentType(
                        MediaType.APPLICATION_JSON).content(jsonObject.toString())
                .param("email", String.valueOf(client.getEmail()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(LicenseType.TRIAL.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(date1.getTime())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(KeyStatus.KEY_GOOD.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void getLicenseByKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        this.mockMvc.perform(get("/license/getLicenseByKey")
                .param("licenseKey", getLicense.getLicenseKey())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(getLicense.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus1.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void changeKeyStatusByLicenseKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        this.mockMvc.perform(get("/license/changeKeyStatusByLicenseKey")
                .param("licenseKey", getLicense.getLicenseKey())
                .param("keyStatus", keyStatus3.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(getLicense.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus3.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void changeLicenseTypeByLicenseKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        this.mockMvc.perform(get("/license/changeLicenseTypeByLicenseKey")
                .param("licenseKey", getLicense.getLicenseKey())
                .param("licenseType", licenseType3.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType3.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(getLicense.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus1.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void changeStartDateByLicenseKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        Date newStartDate = new Date();

        this.mockMvc.perform(get("/license/changeStartDateByLicenseKey")
                .param("licenseKey", getLicense.getLicenseKey())
                .param("startDate", String.valueOf(newStartDate.getTime()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.startDate", Matchers.is(newStartDate.getTime())))
                .andExpect(jsonPath("$.endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus1.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void changeEndDateByLicenseKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        Date newEndDate = new Date();

        this.mockMvc.perform(get("/license/changeEndDateByLicenseKey")
                .param("licenseKey", getLicense.getLicenseKey())
                .param("endDate", String.valueOf(newEndDate.getTime()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(newEndDate.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(license1.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus1.toString())))
                .andDo(print()).andReturn();
    }

    @Test
    //@WithMockUser(username = "admin", roles = {"ANONYMOUS"})
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void deleteLicenseByKey() throws Exception {
        List<License> licenses = (List<License>) licenseRepository.findAll();
        License getLicense = licenses.get(0);

        this.mockMvc.perform(delete("/license/deleteLicenseByKey")
                .param("key", getLicense.getLicenseKey())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseType", Matchers.is(licenseType1.toString())))
                .andExpect(jsonPath("$.endDate", Matchers.is(endDate1.getTime())))
                .andExpect(jsonPath("$.licenseKey", Matchers.is(getLicense.getLicenseKey())))
                .andExpect(jsonPath("$.keyStatus", Matchers.is(keyStatus1.toString())))
                .andDo(print()).andReturn();
    }


}