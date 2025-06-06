package com.ICTL3.Tp318.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ICTL3.Tp318.models.Departement;
import com.ICTL3.Tp318.repositories.DepartementRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartementService {
    
    private final DepartementRepository departement_repository;

    //Add a departement
    public Departement addDepartement(Departement departement){
        
        if(departement_repository.existsDistinctByLibelleDepartement(departement.getLibelleDepartement())){

            throw new IllegalArgumentException("Ce libemlle existe deja");
        }
        return departement_repository.save(departement);
    }

    //Recuperer tous les departements
    public List<Departement> getAllDepartements(){

        return departement_repository.findAll();
    }

    //Recuperer un departement par son id
    public Optional<Departement> getDepartementById(UUID Id){

        return departement_repository.findById(Id);
    }

    //Mise à jour d'un departement
    public Departement updateDepartement(Departement updatedDept, UUID Id){

        return departement_repository.findById(Id)
        .map(
            (existingDept)->{
                existingDept.setLibelleDepartement(updatedDept.getLibelleDepartement());
                return departement_repository.save(existingDept);
            }
        ).orElseThrow(()->new EntityNotFoundException("Departement inexistant"));
    }

    public void deleteDepartement(UUID Id){

        if(!departement_repository.existsById(Id)){
            throw new EntityNotFoundException("Ce departement n'existe pas");
        }
        departement_repository.deleteById(Id);
    }
}
