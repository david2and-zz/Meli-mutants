package com.meli.melimutants.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MutantDnaValidateRequest {
    private String [] dna;
}
