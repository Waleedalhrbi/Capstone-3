package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
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
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "complaint message must not be empty")
    @Column(columnDefinition = "varchar(250) not null")
    private String complaintMessage;

    @AssertFalse
    @JsonIgnore
    private Boolean isApproved;


    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @JsonIgnore
    private Request request;

    @ManyToOne
    @JoinColumn(name = "storage_provider_id", referencedColumnName = "id")
    private StorageProvider storageProvider;
}
