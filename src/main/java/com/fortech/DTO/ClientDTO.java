package com.fortech.DTO;

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
    private String surname;
    private int age;
    private String email;
    private Date created = new Date();
    private List<LicenseDTO> license = new ArrayList<>();

    public ClientDTO() {
    }

    public ClientDTO(String name, String surname, int age, String email, List<LicenseDTO> license) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.license = license;
    }

    @Override
    public String toString() {
        return "{" +
                ", name=\"" + name + '\"' +
                ", surname=\"" + surname + '\"' +
                ", age=" + age +
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        client.setSurname(this.surname);
        client.setAge(this.age);
        client.setEmail(this.email);
        List<License> licenseList = new ArrayList<>();
        this.license.forEach(license1 -> {
            licenseList.add(license1.toEntity());
        });
        client.setLicense(licenseList);
        return client;
    }

}
