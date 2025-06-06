package com.ICTL3.Tp318.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ICTL3.Tp318.dtos.EmployeDTO;
import com.ICTL3.Tp318.models.Poste;
import com.ICTL3.Tp318.models.TypePoste;
import com.ICTL3.Tp318.services.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller()
@RequiredArgsConstructor
@RequestMapping("/poste")
public class PosteController {

    private final EmployeService employe_service;

    private final PosteService poste_service;

    
    //Recuperer la liste des postes et les transmettre à --->
    //---> la page listpostes.html (templates/listpostes) 
    @GetMapping
    public String returnListPostes(Model model) {
        
        List<Poste> postes = poste_service.getAllPostes();

        model.addAttribute("list_postes", postes);

        return "poste_templates/listpostes";
    }

    //Atteindre la page addpostes.html de création d'un --->
    //---> nouveau poste
    @GetMapping("/new")
    public String returnCreationPage(Model model) {
        
        model.addAttribute("poste", new Poste());
        model.addAttribute("libellesPoste", TypePoste.values());

        return "poste_templates/addpostes";
    }


    //Service de sauvegarde des postes
    @PostMapping("/save")
    public String save(
        @Valid @ModelAttribute("poste") Poste poste,
        RedirectAttributes ra,
        BindingResult br,
        Model model

    ){
        if(br.hasErrors()){

            return "poste_templates/addpostes";
        }
        try{
            poste_service.addPoste(poste);
            String message = "Le poste "+ poste.getLibellePoste() + "a ete cree avec succes";
            ra.addFlashAttribute("message", message);

            return "redirect:/poste";

        }catch(IllegalArgumentException e){

            br.rejectValue("libellePoste", "500", e.getMessage());

            return "poste_templates/addpostes";
        }
    }
    

    //Liste des employés pour un poste spécifié via son ID
    @GetMapping("/employes_par_poste/{id}")
    public String returnListEmployeParPoste(
        @PathVariable UUID id,
        Model model
    ) {
        Poste poste = poste_service.getPosteById(id)
        .orElseThrow(() -> new EntityNotFoundException("Le poste est inexistant"));
        
        List<EmployeDTO> listEmployes = employe_service.getEmployesByPoste(id);
        model.addAttribute("listEmplyesByPoste", listEmployes);
        model.addAttribute("libellePoste", poste.getLibellePoste());

        return "poste/liste_employes_par_poste";
    }


    //Page de mise à jour d'un poste via son id
    @GetMapping("edit/{id}")
    public String returnEditPage(
        @PathVariable UUID id,
        Model model,
        RedirectAttributes ra

    ){
        Poste poste = poste_service.getPosteById(id).orElseThrow(
            ()->new EntityNotFoundException(
                "Ce poste est inexistant")
            );

        model.addAttribute("posteToUpdate", poste);
        model.addAttribute("libellesPostes", TypePoste.values());
        
        return "poste_templates/editpostes";
    }


    //service de mise à jour du poste
    @PostMapping("update/{id}")
    public String performUpdate(
        @PathVariable UUID id,
        @Valid @ModelAttribute("posteToUpdate") Poste poste,
        BindingResult br,
        RedirectAttributes ra

        ) {

        if(br.hasErrors()){

            return "poste_templates/editpostes";
        }

        Poste existingPoste = poste_service.getPosteById(id).
        orElseThrow(()-> new EntityNotFoundException("Ce poste est inexistant"));

        try{
            poste_service.updatePoste(id, existingPoste);
            ra.addFlashAttribute("message", 
            "le poste"+existingPoste.getLibellePoste()+"Mise à jour");

            return "redirect:/poste";
        }
        catch(IllegalArgumentException e){

            br.rejectValue("libellePoste", "500", e.getMessage());
            return "poste_templates/editpostes";
        }
        
    }
    
    //Suppprimer un poste
    @GetMapping("delete/{id}")
    public String performDelete(
        @PathVariable UUID id,
        RedirectAttributes ra
        ) {
        
        poste_service.deletePoste(id);
        ra.addFlashAttribute(
            "message",
            "Le poste a ete supprime avec succes");
        
        return "redirect:/poste";
    }
    
}
