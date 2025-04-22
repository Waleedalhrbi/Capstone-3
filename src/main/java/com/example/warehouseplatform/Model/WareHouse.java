package com.example.warehouseplatform.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class WareHouse {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotEmpty(message = "the store type should not be null")
    @Pattern(regexp = "^(?i)(Small|Medium|Large)$")
    @Column(columnDefinition = "varchar(30) not null")
    private String store_type;


    @NotEmpty(message = "the store type should not be null")
    @Pattern(regexp = "^(?i)(Small|Medium|Large)$")
    @Column(columnDefinition = "varchar(30) not null")
    private String store_size;


    @NotEmpty(message = "the location should not be empty")
    @Size(max = 20, message = "the location should be 30 character maximum ")
    @Column(columnDefinition = "varchar(30) not null")
    private String location;

    /// in square meters m^2

    @NotNull(message = "the Storage Area should not be empty")
    @Positive(message = "the Storage Area should be a correct number in square meters ")
    @Min(value = 100, message = "the minimum Storage Area allowed is 100 m^2")
    @Column(columnDefinition = "int not null ")
    private Integer storageArea;


    /// per day
    @NotNull(message = "the price should not be empty")
    @Positive(message = "the Price  should be a correct number ")
    @Min(value = 500, message = "the minimum price for a day is 500 SAR")
    @Column(columnDefinition = "int not null ")
    private Integer price;


    @AssertFalse
    private Boolean isAvailable;


    private Integer usageCount=0;


}
