package com.ICTL3.Tp318.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "employes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employe {

    @Id
    @GeneratedValue
    private UUID id;
	
    @Column(nullable = false)
    @NotBlank(message = "Le nom est requis")
    @Size(min = 2, max = 20, message = "La longueur du nom doit etre entre 2 et 20")
    private String nom;
	
    @Column(nullable = false)
    @Email(message = "L'email doit etre de la forme user@exemple.domain")
    private String email;
	
    @Column(nullable = false)
    @PastOrPresent(message = "La date d'embauche doit inférieure ou égale à aujourd'hui ")
    private LocalDate dateEmbauche;
	
    @Column(nullable = false)
    @Min(value=42000, message = "Le salaire Min est de 42000")
    @Max(value=1000000, message = "Le salaire Max est de 1000000")
    private Integer salaire;

    
    @ManyToOne()
    @JoinColumn(name="poste") // clé etrangere
    private Poste poste;

    @ManyToOne()
    @JoinColumn(name="departement") //cle etrangere
    private Departement departement;

}
