package com.ICTL3.Tp318.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ICTL3.Tp318.models.Poste;
import com.ICTL3.Tp318.repositories.PosteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PosteService {
    
    private final PosteRepository poste_repository;

    //Ajouter un nouveau poste
    public Poste addPoste(Poste poste){

        if(poste_repository.existsDistinctByLibellePoste(poste.getLibellePoste())){

           throw new IllegalArgumentException("Ce libelle existe deja"); 
        }
        return poste_repository.save(poste);
    }

    //Récuperer la liste de tous les postes
    public List<Poste> getAllPostes(){
        
        return poste_repository.findAll();
    }

    //Recuperer un poste par son Id
    public Optional<Poste> getPosteById(UUID Id){

        return poste_repository.findById(Id);
    }

    //Modifier les informations d'un poste
    public Poste updatePoste(UUID Id, Poste updatedPoste){

        return poste_repository.findById(Id).map(

            (existingPoste)->{
                existingPoste.setLibellePoste(updatedPoste.getLibellePoste());
                existingPoste.setSalaireMax(updatedPoste.getSalaireMax());
                existingPoste.setSalaireMin(updatedPoste.getSalaireMin());

                return poste_repository.save(existingPoste); 
            
            }
        ).orElseThrow(()-> new RuntimeException("Poste Non trouve"));
        
    }

    //Supprimer un poste
    public void deletePoste(UUID Id){

        if(!poste_repository.existsById(Id)){

            throw new EntityNotFoundException("Poste Inexistant");
        }

        poste_repository.deleteById(Id);
    }

    
}
