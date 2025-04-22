package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.BookedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookedDateRepository extends JpaRepository<BookedDate,Integer> {

    BookedDate findBookedDateById(Integer id);

    boolean existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);

}
