package com.example.warehouseplatform.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Please enter rating")
    @Positive(message = "Rating must be positive")
    private Integer rating;

    @NotEmpty(message = "Please enter a comment")
    @Size(min = 6, message = "The length of comment must be more than 5")
    @Column(columnDefinition = "VARCHAR(120) NOT NULL")
    private String comment;


    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate review_date;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @JsonIgnore
    private Supplier supplier;


    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @JsonIgnore
    private Request request;
}
