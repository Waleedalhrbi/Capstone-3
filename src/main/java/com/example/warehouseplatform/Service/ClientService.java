package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.DTO.ClientDTO;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Repository.ClientRepository;

import com.example.warehouseplatform.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmailNotificationService emailNotificationService;
    private final RequestRepository requestRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<ClientDTO> getAll() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for(Client c : clients) {
            ClientDTO clientDTO = new ClientDTO(c.getUsername(),c.getEmail(),c.getPhoneNumber());
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public void updateClient(Integer id, Client updatedClient) {
        Client client = clientRepository.findClientById(id);
        if(client == null) throw new ApiException("Client not found");

        client.setUsername(updatedClient.getUsername());
        client.setPassword(updatedClient.getPassword());
        client.setEmail(updatedClient.getEmail());
        client.setPhoneNumber(updatedClient.getPhoneNumber());
        clientRepository.save(client);
    }

    public void deleteClient(Integer id) {
        Client client = clientRepository.findClientById(id);
        if(client == null) throw new ApiException("client not found");
        clientRepository.deleteById(id);
    }

    public Client getClientById(Integer id){
        return clientRepository.findClientById(id);
    }

    public void notifyEndOfRequest(Integer requestId) {
        Request request = requestRepository.findRequestById(requestId); // assuming you have this method

        if (request == null) {
            throw new ApiException("Request not found");
        }

        LocalDate today = LocalDate.now();

        if (request.getEnd_date() != null && request.getEnd_date().isEqual(today)) {
            Client client = request.getClient();

            if (client == null) {
                throw new ApiException("Client not found for this request");
            }

            if (client.getEmail() == null || client.getUsername() == null) {
                throw new ApiException("Client email or username is missing");
            }

            String subject = "Your Warehouse Booking Has Ended";
            String message = "Dear " + client.getUsername() + ",\n\n" +
                    "Your warehouse booking has officially ended on " + today + ".\n\n" +
                    "We hope everything went smoothly. Feel free to leave a review or book again!\n\n" +
                    "Thank you";

            EmailRequest emailRequest = new EmailRequest(client.getEmail(),message, subject);
            emailNotificationService.sendEmail(emailRequest);
        } else {
            throw new ApiException("Today is not the request's end date");
        }
    }
}
