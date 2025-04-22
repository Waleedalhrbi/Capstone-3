package com.example.warehouseplatform.Controller;
import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.BookedDate;
import com.example.warehouseplatform.Service.BookedDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/booked-date")
@RequiredArgsConstructor
public class BookedDateController {




    private final BookedDateService bookedDateService;




    @GetMapping("get")
    public ResponseEntity getLAll (){
        return  ResponseEntity.status(200).body(bookedDateService.getAll());
    }



    @PostMapping("add")
    public ResponseEntity addBookedDate (@Valid @RequestBody BookedDate bookedDate){
        bookedDateService.addBookedDate(bookedDate);
        return ResponseEntity.status(200).body(new ApiResponse("The Booked Date added successfully"));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateBookedDate (@Valid @RequestBody BookedDate bookedDate, @PathVariable Integer id){
        bookedDateService.updateBookedDate(bookedDate,id);
        return ResponseEntity.status(200).body(new ApiResponse("The Booked Date updated successfully"));
    }




    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteBookedDate(@PathVariable Integer id){
        bookedDateService.deleteBookedDate(id);
        return ResponseEntity.status(200).body(new ApiResponse("The Booked Date deleted successfully"));
    }









}
