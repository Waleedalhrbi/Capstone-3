package com.example.warehouseplatform.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

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
    @Column(columnDefinition = "varchar(60) not null")
    private String email;

    @NotEmpty(message = "phone number must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;




    private Integer complainCount=0;
    private Boolean isBlackListed;




    @OneToMany(cascade =CascadeType.ALL, mappedBy = "supplier")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private Set<Review> reviews;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private Set<SupplierComplaint> complaints;

}
