package com.fortech.keyGenerator;

import java.util.Random;

/**
 * Created by iosifvarga on 29.06.2017.
 */
public class generateKey {

    public static String generateLicenseKey() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 23) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());

            if (salt.length() == 5 || salt.length() == 11 || salt.length() == 17)
                salt.append("-");
            else
                salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    /*
    public static Client clientDTOToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setAge(clientDTO.getAge());
        client.setEmail(clientDTO.getEmail());
        List<License> licenseList = new ArrayList<>();
        List<LicenseDTO> licenseDTOS = clientDTO.getLicense();
        licenseDTOS.forEach(licenseDTO -> {
            License license = new License();
            license.setKeyStatus(licenseDTO.getKeyStatus());
            license.setLicenseKey(licenseDTO.getLicenseKey());
            license.setStartDate(licenseDTO.getStartDate());
            license.setEndDate(licenseDTO.getEndDate());
            license.setLicenseType(licenseDTO.getLicenseType());
            licenseList.add(license);
        });
        client.setLicense(licenseList);
        return client;
    }

    public static ClientDTO clientToDto(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setAge(client.getAge());
        clientDTO.setEmail(client.getEmail());
        List<LicenseDTO> licenseDTOS = new ArrayList<>();
        List<License> licenses = client.getLicense();
        licenses.forEach(license -> {
            LicenseDTO licenseDTO = new LicenseDTO();
            licenseDTO.setStartDate(license.getStartDate());
            licenseDTO.setEndDate(license.getEndDate());
            licenseDTO.setKeyStatus(license.getKeyStatus());
            licenseDTO.setLicenseKey(license.getLicenseKey());
            licenseDTO.setLicenseType(license.getLicenseType());
            licenseDTOS.add(licenseDTO);
        });
        clientDTO.setLicense(licenseDTOS);
        return clientDTO;
    }

    public static License licenseDTOtoEntity(LicenseDTO licenseDTO) {
        License license = new License();
        license.setLicenseType(licenseDTO.getLicenseType());
        license.setStartDate(licenseDTO.getStartDate());
        license.setEndDate(licenseDTO.getEndDate());
        license.setLicenseKey(licenseDTO.getLicenseKey());
        license.setKeyStatus(licenseDTO.getKeyStatus());
        return license;
    }

    public static LicenseDTO licenseToDto(License license) {
        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setLicenseType(license.getLicenseType());
        licenseDTO.setStartDate(license.getStartDate());
        licenseDTO.setEndDate(license.getEndDate());
        licenseDTO.setLicenseKey(license.getLicenseKey());
        licenseDTO.setKeyStatus(license.getKeyStatus());
        return licenseDTO;
    }*/

}
