package com.meli.melimutants.controller;

import com.meli.melimutants.service.MutantsValidatorService;
import com.meli.melimutants.service.StatsService;
import com.meli.melimutants.model.request.MutantDnaValidateRequest;
import com.meli.melimutants.model.response.StatsResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
public class MeliMutantsControllerTest {

    @InjectMocks
    private MeliMutantsController meliMutantsController;

    @Mock
    private MutantsValidatorService mutantsValidatorService;

    @Mock
    private StatsService statsService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(meliMutantsController,"mutantsValidatorService",mutantsValidatorService);
        ReflectionTestUtils.setField(meliMutantsController,"statsService",statsService);
    }

    @Test
    public void getStats(){
        Mockito.when(statsService.getStats()).thenReturn(this.getStatsResponse());
        ResponseEntity<StatsResponse> response = meliMutantsController.getStats();
        Assert.assertNotNull(response);
    }

    @Test
    public void validateMutant_getMutant(){
        Mockito.when(mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest())).thenReturn(true);
        ResponseEntity<String> response = meliMutantsController.validateMutant(this.getMutantDnaValidateRequest());
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void validateMutant_getHuman(){
        Mockito.when(mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest())).thenReturn(false);
        ResponseEntity<String> response = meliMutantsController.validateMutant(this.getMutantDnaValidateRequest());
        Assert.assertNotNull(response);
        Assert.assertEquals(503,response.getStatusCodeValue());
    }

    private MutantDnaValidateRequest getMutantDnaValidateRequest(){
        MutantDnaValidateRequest mutantDnaValidateRequest = new MutantDnaValidateRequest();
        String [] dna = {"ATGCGA","CAGTGC","GGGGGG","AGAAGG","CCCCTA","TCCCTG"};
        mutantDnaValidateRequest.setDna(dna);
        return mutantDnaValidateRequest;
    }

    private StatsResponse getStatsResponse(){
        StatsResponse statsResponse = new StatsResponse(1,1,1.0);
        return statsResponse;
    }
}

