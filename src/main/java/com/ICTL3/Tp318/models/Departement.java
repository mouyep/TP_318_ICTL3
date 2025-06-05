package com.ICTL3.Tp318.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "departements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Departement {
    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable=false)
    @NotBlank(message = "le libelle du departement est requis")
	private String libelleDepartement;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<Employe> employes;

}
