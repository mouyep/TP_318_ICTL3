package com.ICTL3.Tp318.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICTL3.Tp318.models.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, UUID>{
    
    List<Employe> findEmployeByPoste_Id(UUID id);
    List<Employe> findEmployeByDepartement_Id(UUID id);
}
