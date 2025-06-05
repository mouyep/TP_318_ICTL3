package com.ICTL3.Tp318.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICTL3.Tp318.models.Poste;
import com.ICTL3.Tp318.models.TypePoste;

@Repository
public interface PosteRepository extends JpaRepository<Poste, UUID>{
    
    boolean existsDistinctByLibellePoste(TypePoste libellePoste);
}
