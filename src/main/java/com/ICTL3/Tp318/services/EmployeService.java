package com.ICTL3.Tp318.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ICTL3.Tp318.dtos.EmployeDTO;
import com.ICTL3.Tp318.models.Employe;
import com.ICTL3.Tp318.repositories.EmployeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeService {
    
    private EmployeRepository employe_repository;

    //add Employe
    public Employe addEmploye(Employe employe){

        return employe_repository.save(employe);
    }

    //Get all employes
    public List<Employe> getAllEmployes(){

        return employe_repository.findAll();
    }

    //Get employe By Id
    public EmployeDTO getEmployeById(UUID Id){

        Employe employe = employe_repository.findById(Id)
        .orElseThrow(
            ()-> new EntityNotFoundException("employe inexistant")
        );
        return mapToDTO(employe);

        
    }

    //Get employees By Department
    public List<EmployeDTO> getEmployesByDepartement(UUID Id){

        return employe_repository.findEmployeByDepartement_Id(Id)
        
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }


    //Get employees By Postes
    public List<EmployeDTO> getEmployesByPoste(UUID Id){

        return employe_repository.findEmployeByPoste_Id(Id)
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }

    //Update employe
    public Employe updateEmploye(Employe updatedEmploye, UUID Id){

        return employe_repository.findById(Id).map(

            (existing_employe)->{
                existing_employe.setDepartement(updatedEmploye.getDepartement());
                existing_employe.setPoste(updatedEmploye.getPoste());
                existing_employe.setEmail(updatedEmploye.getEmail());
                existing_employe.setSalaire(updatedEmploye.getSalaire());

                return employe_repository.save(updatedEmploye);
            }
        ).orElseThrow(()-> new EntityNotFoundException("Employe inexistant"));

    }

    //Supprimer un employe
    public void deleteEmploye(UUID Id){

        if(!employe_repository.existsById(Id)){

            throw new EntityNotFoundException("Employe inexistant");
        }

        employe_repository.deleteById(Id);
    }

    //Convertir un modele Employe en DTO
    public EmployeDTO mapToDTO(Employe employe){

        return new EmployeDTO(

        employe.getId(),
        employe.getNom(),
        employe.getEmail(),
        Period.between(employe.getDateEmbauche(), LocalDate.now()).getYears(),
        employe.getSalaire(),
        employe.getPoste().getLibellePoste(),
        employe.getDepartement().getLibelleDepartement()

    );
        
    }

}
