package com.client.simplerestapi.controller;

import com.client.simplerestapi.dto.ClientDTO;
import com.client.simplerestapi.model.Client;
import com.client.simplerestapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Create a new client (if valid input)
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody Client client, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();  // Returns 400 Bad Request for invalid inputs
        }

        // If valid input, create and return the client
        ClientDTO clientDTO = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);  // Returns 201 Created
    }

    // Update an existing client by ID
    @PutMapping("/update/{idNumber}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable String idNumber, @Valid @RequestBody Client client) {
        Optional<ClientDTO> clientDTOOpt = Optional.ofNullable(clientService.updateClient(idNumber, client));

        if (clientDTOOpt.isEmpty()) {
            return ResponseEntity.notFound().build();  // Returns 404 Not Found if the client doesn't exist
        }

        return ResponseEntity.ok(clientDTOOpt.get());  // Returns 200 OK with the updated client info
    }

    // Search clients by first name, ID number, or mobile number
    @GetMapping("/search")
    public ResponseEntity<List<ClientDTO>> searchClients(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber) {

        List<ClientDTO> clientDTOList = clientService.searchClients(firstName, idNumber, mobileNumber);
        return ResponseEntity.ok(clientDTOList);  // Returns 200 OK with the list of matching clients
    }

    // Get client by ID number (useful for detailed view)
    @GetMapping("/{idNumber}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable String idNumber) {
        Optional<ClientDTO> clientDTOOpt = clientService.getClientById(idNumber);

        return clientDTOOpt.map(ResponseEntity::ok)  // Returns 200 OK if client is found
                .orElseGet(() -> ResponseEntity.notFound().build());  // Returns 404 Not Found if client doesn't exist
    }
}
