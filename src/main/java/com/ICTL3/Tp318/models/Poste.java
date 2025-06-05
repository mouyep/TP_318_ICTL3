package com.ICTL3.Tp318.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="postes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Poste{


    @Id
    @GeneratedValue
	private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "Le libbelle du poste est obligatoire")
	private  TypePoste libellePoste;
	
    @Column(nullable=false)
    @Min(value = 42000, message = "Le salaire Min est 42.000")
    private Integer salaireMin;
	
    @Column(nullable=false)
    @Max(value = 1000000, message="Le salaire max est un million")
    private Integer salaireMax;

    @OneToMany(mappedBy = "poste", cascade = CascadeType.ALL)
    private List<Employe> employes;

    @AssertTrue(message = "Les salaire Max doit etre superieure au saliare Min")
    public boolean isSalaireValide(){
        if(salaireMin == null || salaireMax == null){

            return true;
        }
        return (salaireMax > salaireMin);
    }
}
