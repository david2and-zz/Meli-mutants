package com.meli.melimutants.service;

import com.meli.melimutants.model.MutantRegister;
import com.meli.melimutants.model.request.MutantDnaValidateRequest;
import com.meli.melimutants.repository.MutantDnaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class MutantsValidatorServiceTest {


    @InjectMocks
    private MutantsValidatorServiceImpl mutantsValidatorService;

    @Mock
    private MutantDnaRepository mutantDnaRepository;

    @Mock
    private StatsService statsService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(mutantsValidatorService,"mutantDnaRepository",mutantDnaRepository);
        ReflectionTestUtils.setField(mutantsValidatorService,"statsService",statsService);
    }

    @Test
    public void validateMutant_UniqueDna_Mutant(){
        Mockito.when(mutantDnaRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Boolean response = mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest(true));
        Assert.assertNotNull(response);
        Assert.assertEquals(response,true);
    }

    @Test
    public void validateMutant_UniqueDna_Human(){
        Mockito.when(mutantDnaRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Boolean response = mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest(false));
        Assert.assertNotNull(response);
        Assert.assertEquals(response,false);
    }

    @Test
    public void validateMutant_FindOnDynamo_Mutant(){
        Mockito.when(mutantDnaRepository.findById(Mockito.anyString())).thenReturn(this.getMutantRegister(true));
        Boolean response = mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest(false));
        Assert.assertNotNull(response);
        Assert.assertEquals(response,true);
    }

    @Test
    public void validateMutant_FindOnDynamo_Human(){
        Mockito.when(mutantDnaRepository.findById(Mockito.anyString())).thenReturn(this.getMutantRegister(false));
        Boolean response = mutantsValidatorService.validateMutant(this.getMutantDnaValidateRequest(true));
        Assert.assertNotNull(response);
        Assert.assertEquals(response,false);
    }

    private MutantDnaValidateRequest getMutantDnaValidateRequest(Boolean isMutant){
        MutantDnaValidateRequest mutantDnaValidateRequest = new MutantDnaValidateRequest();
        String [] dna;
        if(isMutant){
            dna = new String[]{"ATGCGA", "CAGTGC", "GGGGGG", "AGAAGG", "CCCCTA", "TCCCTG"};
        }else{
            dna = new String[]{"TATGCA", "TACGAT", "ATGCAA", "TACGCT", "ATGCAT", "TACGAC"};
        }
        mutantDnaValidateRequest.setDna(dna);
        return mutantDnaValidateRequest;
    }

    private Optional<MutantRegister> getMutantRegister(Boolean value){
        MutantRegister mutantRegister = new MutantRegister();
        mutantRegister.setIsMutant(value);
        mutantRegister.setIddna("TESTID");
        mutantRegister.setIddna("[\"ATGCGA\",\"CAGTGC\",\"GGGGGG\",\"AGAAGG\",\"CCCCTA\",\"TCCCTG\"]");
        return Optional.of(mutantRegister);
    }
}
