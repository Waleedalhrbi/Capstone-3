package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.BookedDate;
import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.WareHouse;
import com.example.warehouseplatform.Repository.BookedDateRepository;
import com.example.warehouseplatform.Repository.RequestRepository;
import com.example.warehouseplatform.Repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final ClientService clientService;
    private final WareHouseRepository wareHouseRepository;
    private final BookedDateRepository bookedDateRepository;


    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public void addRequest(Request request, Integer clientId, Integer warehouseId) {

        Client client = clientService.getClientById(clientId);
        if (client == null) throw new ApiException("Client not found");

        WareHouse wareHouse = wareHouseRepository.findWareHouseById(warehouseId);
        if (wareHouse == null) throw new ApiException("Warehouse not found");


        long days = java.time.temporal.ChronoUnit.DAYS.between(request.getStart_date(), request.getEnd_date());
        if (days <= 0) {
            throw new ApiException("End date must be after start date");
        }


        int totalPrice = (int) days * wareHouse.getPrice();


        boolean isBooked = bookedDateRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                request.getEnd_date(), request.getStart_date());
        if (isBooked) {
            throw new ApiException("Selected dates are already booked");
        }


        if (!wareHouse.getStore_type().equalsIgnoreCase(request.getStore_type()) ||
                !wareHouse.getStore_size().equalsIgnoreCase(request.getStore_size())) {
            throw new ApiException("Store type or size does not match the warehouse");
        }


        BookedDate bookedDate = new BookedDate();
        bookedDate.setStartDate(request.getStart_date());
        bookedDate.setEndDate(request.getEnd_date());
        bookedDateRepository.save(bookedDate);


        request.setClient(client);
        request.setWareHouse(wareHouse);
        request.setTotal_price(totalPrice);

        requestRepository.save(request);
    }



    public Request getRequestById(Integer id){
        return requestRepository.findRequestById(id);
    }
}
