package com.example.warehouseplatform.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Admin {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


    @NotEmpty(message = "the user should not be empty")
    @Size(min = 5,max = 8 ,message = "the username size must be from 5 to 8 characters")
    @Column(columnDefinition = "varchar(8) not null unique")
    private String username ;

    @NotEmpty(message = "the password should not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @Column(columnDefinition = "varchar(12) not null")
    private String password;



}
