package com.ICTL3.Tp318.dtos;

import java.util.UUID;

import com.ICTL3.Tp318.models.TypePoste;

public record EmployeDTO(

UUID id,
String nom,
String email,
Integer anciennete,
Integer salaire,
TypePoste poste,
String departement

){}
