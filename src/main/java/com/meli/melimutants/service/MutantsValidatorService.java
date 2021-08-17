package com.meli.melimutants.service;

import com.meli.melimutants.model.request.MutantDnaValidateRequest;

public interface MutantsValidatorService {
    Boolean validateMutant(MutantDnaValidateRequest request);
}
