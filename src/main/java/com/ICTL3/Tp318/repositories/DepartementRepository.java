package com.ICTL3.Tp318.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICTL3.Tp318.models.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, UUID>{

    boolean existsDistinctByLibelleDepartement(String libelleDepartement);
}
