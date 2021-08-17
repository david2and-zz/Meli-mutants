package com.meli.melimutants.controller;


import com.meli.melimutants.service.MutantsValidatorService;
import com.meli.melimutants.service.StatsService;
import com.meli.melimutants.model.request.MutantDnaValidateRequest;
import com.meli.melimutants.model.response.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeliMutantsController {

    private final MutantsValidatorService mutantsValidatorService;
    private final StatsService statsService;

    @GetMapping(value= "/stats")
    public ResponseEntity<StatsResponse> getStats(){
        return new ResponseEntity<>(statsService.getStats(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/mutant")
    public ResponseEntity<String> validateMutant (@RequestBody MutantDnaValidateRequest request) {
        if(mutantsValidatorService.validateMutant(request)){
            return new ResponseEntity<>("",HttpStatus.OK);
        }
        return new ResponseEntity<>("",HttpStatus.SERVICE_UNAVAILABLE);
    }
}
