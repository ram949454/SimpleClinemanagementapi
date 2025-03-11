package com.client.simplerestapi.dto;

import com.client.simplerestapi.model.Client;

public class ClientDTO {

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String idNumber;
    private String physicalAddress; // Add this field

    // Constructor without physical address
    public ClientDTO(String firstName, String lastName, String mobileNumber, String idNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.idNumber = idNumber;
    }

    // Constructor with physical address
    public ClientDTO(String firstName, String lastName, String mobileNumber, String idNumber, String physicalAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.idNumber = idNumber;
        this.physicalAddress = physicalAddress;  // Set the physical address
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;  // Get physical address
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress; // Set physical address
    }
}
