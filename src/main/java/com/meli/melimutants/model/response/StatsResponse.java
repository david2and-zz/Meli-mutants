package com.meli.melimutants.model.response;

import com.meli.melimutants.model.StatsRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class StatsResponse {
    public StatsResponse(StatsRegister statsRegister){
        this.count_mutant_dna = statsRegister.getMutantDnaValidated();
        this.count_human_dna = statsRegister.getHumanDnaValidated();
        this.ratio = statsRegister.getRatio();
    }

    private Integer count_mutant_dna;
    private Integer count_human_dna;
    private Double ratio;
}
