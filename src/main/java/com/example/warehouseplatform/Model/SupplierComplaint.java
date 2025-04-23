package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
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
public class SupplierComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "complaint message must not be empty")
    @Column(columnDefinition = "varchar(250) not null")
    private String complaintMessage;

    @AssertFalse
    private Boolean isApproved;


    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @JsonIgnore
    private Request request;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @JsonIgnore
    private Supplier supplier;

}
