package com.example.warehouseplatform.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Client {

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
    @Column(columnDefinition = "varchar(20) not null")
    private String email;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer balance;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Review> reviews;
}
