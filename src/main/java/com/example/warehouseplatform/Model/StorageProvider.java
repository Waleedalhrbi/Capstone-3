package com.example.warehouseplatform.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

    @NotEmpty
    @Column(columnDefinition = "varchar(10) not null")
    private String username;

    @NotEmpty
    @Column(columnDefinition = "varchar(10) not null")
    private String password;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(30) not null")
    private String email;

    @NotEmpty
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    private Boolean isActive;

    @NotEmpty
    @Column(columnDefinition = "varchar(60) not null")
    private String propertyLicence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageProvider")
    private Set<Employee> employees;
}
