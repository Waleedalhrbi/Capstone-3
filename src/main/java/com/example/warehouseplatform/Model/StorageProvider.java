package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class StorageProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "username must not be empty")
    @Column(columnDefinition = "varchar(10) not null")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Column(columnDefinition = "varchar(10) not null")
    private String password;

    @NotEmpty(message = "email must not be empty")
    @Email
    @Column(columnDefinition = "varchar(30) not null")
    private String email;

    @NotEmpty(message = "phone number must not be empty")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    private Boolean isActive;

    @NotEmpty(message = "licence must not be empty")
    @Column(columnDefinition = "varchar(60) not null")
    private String propertyLicence;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate licenceDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<WareHouse> wareHouses;
}
