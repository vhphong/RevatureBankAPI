package com.revature.projects.repositories;

import com.revature.projects.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Employee> findEmployeeByName(String nameInput) {
        String sql = "SELECT e FROM Employee e WHERE e.employeeName LIKE :emplName";
        final TypedQuery<Employee> query = entityManager.createQuery(sql, Employee.class);
        query.setParameter("emplName", "%" + nameInput + "%");

        return query.getResultList();
    }

    @Override
    public List<Employee> findEmployeeByActiveStatus(int statusInput) {
        String sql = "SELECT e FROM Employee e WHERE e.activeStatus = :emplStatus";
        final TypedQuery<Employee> query = entityManager.createQuery(sql, Employee.class);
        query.setParameter("emplStatus", statusInput);

        return query.getResultList();
    }

}
