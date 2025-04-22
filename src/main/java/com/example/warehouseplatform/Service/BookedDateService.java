package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.BookedDate;
import com.example.warehouseplatform.Repository.BookedDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedDateService {

private final BookedDateRepository bookedDateRepository;


public List<BookedDate> getAll(){
    return bookedDateRepository.findAll();
}



    public void addBookedDate(BookedDate bookedDate) {
        bookedDateRepository.save(bookedDate);
    }



    public void updateBookedDate(BookedDate bookedDate, Integer id) {

        BookedDate bookedDate1 = bookedDateRepository.findBookedDateById(id);

        if (bookedDate1 == null) {
            throw new ApiException("the Booked Date is not found");
        }

        bookedDate1.setStartDate(bookedDate.getStartDate());
        bookedDate1.setEndDate(bookedDate.getEndDate());
        bookedDateRepository.save(bookedDate1);


    }




    public void deleteBookedDate(Integer id){

        BookedDate bookedDateBy= bookedDateRepository.findBookedDateById(id);

        if (bookedDateBy==null){
            throw new ApiException("The Booked DateBy is not found");
        }

        bookedDateRepository.delete(bookedDateBy);


    }





}
