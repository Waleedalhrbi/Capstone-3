package com.example.warehouseplatform.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WareHouseDTO {



    private StorageProviderDTO storageProviderDTO;


    @NotEmpty(message = "the store type should not be null")
    @Pattern(regexp = "^(?i)(Small|Medium|Large)$")
    private String storeSize;


    @NotEmpty(message = "the location should not be empty")
    @Size(max = 20, message = "the location should be 30 character maximum ")
    private String location;

    /// in square meters m^2

    @NotNull(message = "the Storage Area should not be empty")
    @Positive(message = "the Storage Area should be a correct number in square meters ")
    @Min(value = 100, message = "the minimum Storage Area allowed is 100 m^2")
    private Integer storageArea;


    /// per day
    @NotNull(message = "the price should not be empty")
    @Positive(message = "the Price  should be a correct number ")
    @Min(value = 100, message = "the minimum price for a day is 500 SAR")
    private Integer price;





}
