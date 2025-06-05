package com.ICTL3.Tp318.dtos;

import java.util.UUID;

public record PosteDTO (

    UUID id,
    String libellePoste,
    Integer salaireMin,
    Integer SlaireMax

){}
