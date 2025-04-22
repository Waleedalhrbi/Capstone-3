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

import java.time.LocalDate;
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





        request.setRequest_date(LocalDate.now());
        request.setClient(client);
        request.setWareHouse(wareHouse);
        wareHouse.setUsageCount(wareHouse.getUsageCount() + 1);
        request.setTotal_price(totalPrice);

        requestRepository.save(request);
    }


    public void updateRequest(Integer id, Request updatedRequest) {
        Request existingRequest = requestRepository.findRequestById(id);
        if (existingRequest == null) {
            throw new ApiException("Request not found");
        }

        WareHouse wareHouse = existingRequest.getWareHouse();
        if (wareHouse == null) {
            throw new ApiException("Warehouse not found");
        }

        long days = java.time.temporal.ChronoUnit.DAYS.between(updatedRequest.getStart_date(), updatedRequest.getEnd_date());
        if (days <= 0) {
            throw new ApiException("End date must be after start date");
        }


        boolean isBooked = bookedDateRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                updatedRequest.getEnd_date(), updatedRequest.getStart_date());
        if (isBooked && !updatedRequest.getStart_date().equals(existingRequest.getStart_date())) {
            throw new ApiException("Selected dates are already booked");
        }

        if (!wareHouse.getStore_type().equalsIgnoreCase(updatedRequest.getStore_type()) ||
                !wareHouse.getStore_size().equalsIgnoreCase(updatedRequest.getStore_size())) {
            throw new ApiException("Store type or size does not match the warehouse");
        }

        int totalPrice = (int) days * wareHouse.getPrice();


        existingRequest.setStore_size(updatedRequest.getStore_size());
        existingRequest.setStore_type(updatedRequest.getStore_type());
        existingRequest.setRequest_employee(updatedRequest.getRequest_employee());
        existingRequest.setStart_date(updatedRequest.getStart_date());
        existingRequest.setEnd_date(updatedRequest.getEnd_date());
        existingRequest.setTotal_price(totalPrice);

        requestRepository.save(existingRequest);
    }

    public void deleteRequest(Integer id) {
        Request request = requestRepository.findRequestById(id);
        if (request == null) {
            throw new ApiException("Request not found");
        }


        LocalDate currentDate = LocalDate.now();
        if (request.getStart_date().minusDays(1).isBefore(currentDate)) {
            throw new ApiException("Request cannot be canceled less than 1 day before the start date");
        }


        WareHouse wareHouse = request.getWareHouse();
        if (wareHouse != null && wareHouse.getUsageCount() > 0) {
            wareHouse.setUsageCount(wareHouse.getUsageCount() - 1);
            wareHouseRepository.save(wareHouse);
        }


        BookedDate bookedDate = bookedDateRepository.findByStartDateAndEndDate(
                request.getStart_date(), request.getEnd_date());
        if (bookedDate != null) {
            bookedDateRepository.delete(bookedDate);
        }


        requestRepository.delete(request);
    }




    public Request getRequestById(Integer id){
        return requestRepository.findRequestById(id);
    }


    public List<Request> getRequestsByDateRange(LocalDate startDate, LocalDate endDate) {
        return requestRepository.findRequestsBetweenDates(startDate, endDate);
    }


    public void checkDateAvailability(LocalDate startDate, LocalDate endDate) {

        boolean isBooked = bookedDateRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                endDate, startDate);

        if (isBooked) {
            throw new ApiException("The selected dates are already booked");
        }
    }


}
