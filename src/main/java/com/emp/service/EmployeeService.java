package com.emp.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.entity.PhoneNumber;
import com.emp.repo.EmployeeRepo;
import com.emp.repo.PhoneNumberRepo;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private PhoneNumberRepo phoneNumberRepository;

    public void addEmployee(Employee employee) {
        // Save employee and associated phone numbers
        for (PhoneNumber phoneNumber : employee.getPhoneNumbers()) {
            phoneNumber.setEmployee(employee);
        }
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }


    public double calculateTax(Employee employee) {
        double totalSalary = calculateTotalSalary(employee);
        double tax = 0;

        if (totalSalary <= 250000) {
            // No Tax
            tax = 0;
        } else if (totalSalary > 250000 && totalSalary <= 500000) {
            // 5% Tax
            tax = (totalSalary - 250000) * 0.05;
        } else if (totalSalary > 500000 && totalSalary <= 1000000) {
            // 10% Tax
            tax = 12500 + (totalSalary - 500000) * 0.1;
        } else {
            // 20% Tax
            tax = 62500 + (totalSalary - 1000000) * 0.2;
        }

        // Add Cess for salary above 2500000
        if (totalSalary > 2500000) {
            double cess = (totalSalary - 2500000) * 0.02;
            tax += cess;
        }

        return tax;
    }

    private double calculateTotalSalary(Employee employee) {
        Calendar dojCalendar = Calendar.getInstance();
        dojCalendar.setTime(employee.getDoj());
        int dojMonth = dojCalendar.get(Calendar.MONTH);
        int dojYear = dojCalendar.get(Calendar.YEAR);

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);

        int monthsWorked = (currentYear - dojYear) * 12 + (currentMonth - dojMonth) + 1;
        double totalSalary = employee.getSalary() * monthsWorked;

        return totalSalary;
    }
}
