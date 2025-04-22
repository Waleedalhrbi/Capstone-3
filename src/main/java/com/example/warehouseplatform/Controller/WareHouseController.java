package com.example.warehouseplatform.Controller;
import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.WareHouse;
import com.example.warehouseplatform.Service.WareHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
public class WareHouseController {




    private final WareHouseService wareHouseService;




    @GetMapping("get")
    public ResponseEntity getLAll (){
        return  ResponseEntity.status(200).body(wareHouseService.getAll());
    }



    @PostMapping("add")
    public ResponseEntity addWareHouse (@Valid @RequestBody WareHouse wareHouse){
        wareHouseService.addWareHouse(wareHouse);
        return ResponseEntity.status(200).body(new ApiResponse("wareHouse added successfully"));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateWareHouse (@Valid @RequestBody WareHouse wareHouse, @PathVariable Integer id){
        wareHouseService.updateWareHouse(wareHouse,id);
        return ResponseEntity.status(200).body(new ApiResponse("wareHouse updated successfully"));
    }




    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteWareHouse(@PathVariable Integer id){
        wareHouseService.deleteWareHouse(id);
        return ResponseEntity.status(200).body(new ApiResponse("wareHouse deleted successfully"));
    }









}
