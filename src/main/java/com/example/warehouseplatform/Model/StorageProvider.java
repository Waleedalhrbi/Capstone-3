package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @AssertFalse
    @JsonIgnore
    private Boolean isActive;

    @NotEmpty(message = "licence must not be empty")
    @Column(columnDefinition = "varchar(60) not null")
    private String license;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonIgnore
    private LocalDate licenseDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<WareHouse> wareHouses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<Complaint> complaints;
}
