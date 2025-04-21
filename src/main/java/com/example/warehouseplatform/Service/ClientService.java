package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.DTO.ClientDTO;
import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<ClientDTO> getAll() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for(Client c : clients) {
            ClientDTO clientDTO = new ClientDTO(c.getUsername(),c.getEmail(),c.getPhoneNumber(),c.getBalance());
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
        client.setBalance(updatedClient.getBalance());
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
}
