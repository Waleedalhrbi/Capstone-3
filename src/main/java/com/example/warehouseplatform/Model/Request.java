package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "Please enter if you request employee ")
    private Boolean request_employee;


    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate start_date;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate end_date;

    private Integer Total_price;

    @ManyToOne
    @JoinColumn(name ="supplier_id", referencedColumnName = "id")
    @JsonIgnore
    private Supplier supplier;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate request_date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Set<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @JsonIgnore
    private WareHouse wareHouse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Set<ProviderComplaint> providerComplaints;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Set<SupplierComplaint> supplierComplaints;
}
