package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.DTO.EmployeeDTO;
import com.example.warehouseplatform.Model.Employee;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Repository.EmployeeRepository;
import com.example.warehouseplatform.Repository.StorageProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final StorageProviderRepository storageProviderRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Employee e : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(e.getFullName(), e.getPhoneNumber(), e.getNationality());
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public void assignEmployeeToProvider(Integer employeeId, Integer providerId) {
        StorageProvider storageProvider = storageProviderRepository.findStorageProviderById(providerId);
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if(storageProvider == null) {
            throw new ApiException("provider not found");
        }

        if(employee == null) {
            throw new ApiException("employee not found");
        }

        employee.setStorageProvider(storageProvider);
        employeeRepository.save(employee);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Integer id, Employee employee) {
        Employee oldEmployee = employeeRepository.findEmployeeById(id);
        if (oldEmployee == null) throw new ApiException("Employee not found");

        oldEmployee.setFullName(employee.getFullName());
        oldEmployee.setPhoneNumber(employee.getPhoneNumber());
        oldEmployee.setNationality(employee.getNationality());
        oldEmployee.setStorageProvider(employee.getStorageProvider());

        employeeRepository.save(oldEmployee);
    }

    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) throw new ApiException("Employee not found");
        employeeRepository.deleteById(id);
    }
}
