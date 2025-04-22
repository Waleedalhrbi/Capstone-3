package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final ClientService clientService;


    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public void addRequest(Request request, Integer clientId) {

        Client client = clientService.getClientById(clientId);
        if (client == null) {
            throw new ApiException("Client not found");
        }


        request.setClient(client);
        request.setIsApproved(false);

        requestRepository.save(request);
    }


    public void updateRequest(Integer requestId, Request newRequestData) {
        Request existingRequest = requestRepository.findRequestById(requestId);

        if (existingRequest == null) {
            throw new ApiException("Request not found");
        }

        existingRequest.setStore_type(newRequestData.getStore_type());
        existingRequest.setStore_size(newRequestData.getStore_size());
        existingRequest.setRequest_employee(newRequestData.getRequest_employee());
        existingRequest.setStart_date(newRequestData.getStart_date());
        existingRequest.setEnd_date(newRequestData.getEnd_date());

        requestRepository.save(existingRequest);
    }


    public void deleteRequest(Integer requestId) {
        Request request = requestRepository.findRequestById(requestId);

        if (request == null) {
            throw new ApiException("Request not found");
        }

        requestRepository.delete(request);
    }


    public Request getRequestById(Integer id){
        return requestRepository.findRequestById(id);
    }
}
