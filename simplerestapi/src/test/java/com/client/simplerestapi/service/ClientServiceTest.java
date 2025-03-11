package com.client.simplerestapi.service;

import com.client.simplerestapi.dto.ClientDTO;
import com.client.simplerestapi.exception.DuplicateIdNumberException;
import com.client.simplerestapi.exception.DuplicateMobileNumberException;
import com.client.simplerestapi.exception.ValidationException;
import com.client.simplerestapi.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        // Initialize the client service
        clientService = new ClientService();
        // Clear any previously created clients before each test
        ClientService.clearClients();
    }

    @Test
    void testCreateClientSuccess() {
        Client client = new Client();
        client.setFirstName("Ramesh");
        client.setLastName("Akkireddi");
        client.setMobileNumber("0812345670");
        client.setIdNumber("9001015800011");
        client.setPhysicalAddress("123 Main St");

        ClientDTO createdClient = clientService.createClient(client);

        assertNotNull(createdClient);
        assertEquals("Ramesh", createdClient.getFirstName());
        assertEquals("9001015800011", createdClient.getIdNumber());
    }

    @Test
    void testCreateClientWithInvalidID() {
        Client client = new Client();
        client.setFirstName("Ramesh");
        client.setLastName("Akkireddi");
        client.setMobileNumber("0812345670");
        client.setIdNumber("123456"); // Invalid ID

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clientService.createClient(client);
        });

        assertEquals("Invalid South African ID number", exception.getMessage());
    }

    @Test
    void testCreateClientWithDuplicateID() {
        Client client1 = new Client();
        client1.setFirstName("Ramesh");
        client1.setLastName("Akkireddi");
        client1.setMobileNumber("0712345670");
        client1.setIdNumber("9001015800011");

        Client client2 = new Client();
        client2.setFirstName("Suresh");
        client2.setLastName("Kumar");
        client2.setMobileNumber("0712345671");
        client2.setIdNumber("9001015800011"); // Duplicate ID

        clientService.createClient(client1);

        DuplicateIdNumberException exception = assertThrows(DuplicateIdNumberException.class, () -> {
            clientService.createClient(client2);
        });

        assertEquals("ID Number already exists", exception.getMessage());
    }

    @Test
    void testCreateClientWithDuplicateMobileNumber() {
        Client client1 = new Client();
        client1.setFirstName("Ramesh");
        client1.setLastName("Akkireddi");
        client1.setMobileNumber("0712345670");
        client1.setIdNumber("9001015800012");

        Client client2 = new Client();
        client2.setFirstName("Rajesh");
        client2.setLastName("Kumar");
        client2.setMobileNumber("0712345670"); // Duplicate mobile number
        client2.setIdNumber("9001015800013");

        clientService.createClient(client1);

        DuplicateMobileNumberException exception = assertThrows(DuplicateMobileNumberException.class, () -> {
            clientService.createClient(client2);
        });

        assertEquals("Mobile number already exists", exception.getMessage());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.setFirstName("Ramesh");
        client.setLastName("Akkireddi");
        client.setMobileNumber("0812345670");
        client.setIdNumber("9001015800011");
        client.setPhysicalAddress("123 Main St");

        clientService.createClient(client);

        Client updatedClient = new Client();
        updatedClient.setFirstName("Rameshwar");
        updatedClient.setLastName("Akkireddi");
        updatedClient.setMobileNumber("0812345671");
        updatedClient.setPhysicalAddress("456 Elm St");

        ClientDTO updatedClientDTO = clientService.updateClient("9001015800011", updatedClient);

        assertNotNull(updatedClientDTO);
        assertEquals("Rameshwar", updatedClientDTO.getFirstName());
        assertEquals("0812345671", updatedClientDTO.getMobileNumber());
        assertEquals("456 Elm St", updatedClientDTO.getPhysicalAddress());
    }

    @Test
    void testSearchClients() {
        Client client1 = new Client();
        client1.setFirstName("Ramesh");
        client1.setLastName("Akkireddi");
        client1.setMobileNumber("0812345670");
        client1.setIdNumber("9001015800011");

        Client client2 = new Client();
        client2.setFirstName("Rajesh");
        client2.setLastName("Kumar");
        client2.setMobileNumber("0812345671");
        client2.setIdNumber("9001015800012");

        clientService.createClient(client1);
        clientService.createClient(client2);

        List<ClientDTO> foundClients = clientService.searchClients("Ramesh", null, null);

        assertEquals(1, foundClients.size());
        assertEquals("Ramesh", foundClients.get(0).getFirstName());
    }

    @Test
    void testGetClientById() {
        Client client = new Client();
        client.setFirstName("Ramesh");
        client.setLastName("Akkireddi");
        client.setMobileNumber("0812345670");
        client.setIdNumber("9001015800011");

        clientService.createClient(client);

        Optional<ClientDTO> foundClient = clientService.getClientById("9001015800011");

        assertTrue(foundClient.isPresent());
        assertEquals("Ramesh", foundClient.get().getFirstName());
    }
}
