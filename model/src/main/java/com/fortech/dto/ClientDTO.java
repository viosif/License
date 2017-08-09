package com.fortech.dto;

import com.fortech.model.Client;
import com.fortech.model.License;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iosifvarga on 07.07.2017.
 */
public class ClientDTO extends BaseDTO<Client> {

    private String name;
    private String extraInformations;
    private String email;
    private Date created = new Date();
    private List<LicenseDTO> license = new ArrayList<>();

    public ClientDTO() {
    }

    public ClientDTO(String name, String extraInformations, String email, List<LicenseDTO> license) {
        this.name = name;
        this.extraInformations = extraInformations;
        this.email = email;
        this.license = license;
    }

    @Override
    public String toString() {
        return "{" +
                ", name=\"" + name + '\"' +
                ", extraInformations=\"" + extraInformations + '\"' +
                ", email=\"" + email + '\"' +
                ", created=\"" + created.getTime() + '\"' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtraInformations() {
        return extraInformations;
    }

    public void setExtraInformations(String extraInformations) {
        this.extraInformations = extraInformations;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<LicenseDTO> getLicense() {
        return license;
    }

    public void setLicense(List<LicenseDTO> license) {
        this.license = license;
    }

    public Client toEntity() {
        Client client = new Client();
        client.setName(this.name);
        client.setExtraInformations(this.extraInformations);
        client.setEmail(this.email);
        List<License> licenseList = new ArrayList<>();
        this.license.forEach(license1 -> {
            licenseList.add(license1.toEntity());
        });
        client.setLicense(licenseList);
        return client;
    }

}