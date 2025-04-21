package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientById(Integer id);
}
