package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Model.BookedDate;
import com.example.warehouseplatform.Model.Supplier;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.WareHouse;
import com.example.warehouseplatform.Repository.BookedDateRepository;
import com.example.warehouseplatform.Repository.RequestRepository;
import com.example.warehouseplatform.Repository.SupplierRepository;
import com.example.warehouseplatform.Repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final SupplierRepository supplierRepository;
    private final WareHouseRepository wareHouseRepository;
    private final BookedDateRepository bookedDateRepository;
    private final EmailNotificationService emailNotificationService;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public void addRequest(Request request, Integer supplierId, Integer warehouseId) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) throw new ApiException("supplier not found");

        if (supplier.getIsBlackListed()) throw new ApiException("supplier is black listed");

        WareHouse wareHouse = wareHouseRepository.findWareHouseById(warehouseId);
        if (wareHouse == null) throw new ApiException("Warehouse not found");

        long days = ChronoUnit.DAYS.between(request.getStart_date(), request.getEnd_date());
        if (days <= 0) {
            throw new ApiException("End date must be after start date");
        }

        boolean isBooked = bookedDateRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                request.getEnd_date(), request.getStart_date());
        if (isBooked) {
            throw new ApiException("Selected dates are already booked");
        }

        int totalPrice = (int) days * wareHouse.getPrice();

        BookedDate bookedDate = new BookedDate();
        bookedDate.setStartDate(request.getStart_date());
        bookedDate.setEndDate(request.getEnd_date());
        bookedDateRepository.save(bookedDate);

        request.setRequest_date(LocalDate.now());
        request.setSupplier(supplier);
        request.setWareHouse(wareHouse);
        request.setTotal_price(totalPrice);
        wareHouse.setUsageCount(wareHouse.getUsageCount() + 1);

        requestRepository.save(request);

        String subject = "Warehouse Request Confirmation";
        String message = "Dear " + supplier.getUsername() + ",\n\n" +
                "Your request for a " + wareHouse.getStoreSize() + " " + wareHouse.getStoreType() +
                " warehouse has been received.\n\nStart Date: " + request.getStart_date() +
                "\nEnd Date: " + request.getEnd_date() +
                "\nTotal Price: " + request.getTotal_price() + " Riyals";

        EmailRequest emailRequest = new EmailRequest(supplier.getEmail(), message, subject);
        emailNotificationService.sendEmail(emailRequest);
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

        long days = ChronoUnit.DAYS.between(updatedRequest.getStart_date(), updatedRequest.getEnd_date());
        if (days <= 0) {
            throw new ApiException("End date must be after start date");
        }

        boolean isBooked = bookedDateRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                updatedRequest.getEnd_date(), updatedRequest.getStart_date());

        if (isBooked && !updatedRequest.getStart_date().equals(existingRequest.getStart_date())) {
            throw new ApiException("Selected dates are already booked");
        }

        int totalPrice = (int) days * wareHouse.getPrice();

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
