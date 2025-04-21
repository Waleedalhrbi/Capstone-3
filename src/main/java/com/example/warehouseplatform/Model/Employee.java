package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    @NotEmpty
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty
    @Column(columnDefinition = "varchar(12) not null")
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "storage_provider_id", referencedColumnName = "id")
    @JsonIgnore
    private StorageProvider storageProvider;
}
