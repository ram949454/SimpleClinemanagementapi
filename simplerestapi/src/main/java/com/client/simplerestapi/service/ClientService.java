package com.client.simplerestapi.service;

import com.client.simplerestapi.dto.ClientDTO;
import com.client.simplerestapi.model.Client;
import com.client.simplerestapi.exception.DuplicateIdNumberException;
import com.client.simplerestapi.exception.DuplicateMobileNumberException;
import com.client.simplerestapi.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClientService {

    private static final List<Client> clients = new ArrayList<>();

    // Validate South African ID number format
    private boolean isValidIdNumber(String idNumber) {
        return Pattern.matches("\\d{13}", idNumber);  // Simple length check
    }

    public ClientDTO createClient(Client client) {
        // Validate ID number first
        if (!isValidIdNumber(client.getIdNumber())) {
            throw new ValidationException("Invalid South African ID number");
        }

        // Check for duplicate ID number or mobile number
        for (Client existingClient : clients) {
            if (existingClient.getIdNumber().equals(client.getIdNumber())) {
                throw new DuplicateIdNumberException("ID Number already exists");
            }
            if (existingClient.getMobileNumber().equals(client.getMobileNumber())) {
                throw new DuplicateMobileNumberException("Mobile number already exists");
            }
        }

        // Add client if validation passes
        clients.add(client);
        // Return the client as DTO, now including physical address
        return new ClientDTO(client.getFirstName(), client.getLastName(), client.getMobileNumber(), client.getIdNumber(), client.getPhysicalAddress());
    }

    public ClientDTO updateClient(String idNumber, Client updatedClient) {
        for (Client existingClient : clients) {
            if (existingClient.getIdNumber().equals(idNumber)) {
                existingClient.setFirstName(updatedClient.getFirstName());
                existingClient.setLastName(updatedClient.getLastName());
                existingClient.setMobileNumber(updatedClient.getMobileNumber());
                existingClient.setPhysicalAddress(updatedClient.getPhysicalAddress());

                // Return the updated client as DTO, including physical address
                return new ClientDTO(existingClient.getFirstName(), existingClient.getLastName(),
                        existingClient.getMobileNumber(), existingClient.getIdNumber(), existingClient.getPhysicalAddress());
            }
        }
        return null; // Return null if client with that ID number is not found
    }

    public List<ClientDTO> searchClients(String firstName, String idNumber, String mobileNumber) {
        List<ClientDTO> result = new ArrayList<>();

        // Search based on provided parameters
        for (Client client : clients) {
            boolean matches = true;

            if (firstName != null && !client.getFirstName().equals(firstName)) {
                matches = false;
            }
            if (idNumber != null && !client.getIdNumber().equals(idNumber)) {
                matches = false;
            }
            if (mobileNumber != null && !client.getMobileNumber().equals(mobileNumber)) {
                matches = false;
            }

            if (matches) {
                // Add client as DTO, including physical address
                result.add(new ClientDTO(client.getFirstName(), client.getLastName(),
                        client.getMobileNumber(), client.getIdNumber(), client.getPhysicalAddress()));
            }
        }

        return result;
    }

    public Optional<ClientDTO> getClientById(String idNumber) {
        // Look for a client with the matching ID number
        for (Client client : clients) {
            if (client.getIdNumber().equals(idNumber)) {
                return Optional.of(new ClientDTO(client.getFirstName(), client.getLastName(),
                        client.getMobileNumber(), client.getIdNumber(), client.getPhysicalAddress())); // Include physical address
            }
        }
        return Optional.empty(); // Return empty if not found
    }

    // This method is for clearing the client list in tests
    public static void clearClients() {
        clients.clear();
    }
}
