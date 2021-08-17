package com.meli.melimutants.service;

import com.meli.melimutants.model.MutantRegister;
import com.meli.melimutants.model.request.MutantDnaValidateRequest;
import com.meli.melimutants.repository.MutantDnaRepository;
import com.meli.melimutants.utils.RegisterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MutantsValidatorServiceImpl implements MutantsValidatorService {

    private final MutantDnaRepository mutantDnaRepository;
    private final StatsService statsService;

    private MutantRegister getResultById(String id) {
        Optional<MutantRegister> mutantRegister = mutantDnaRepository.findById(id);
        if (mutantRegister.isPresent()) {
            return mutantRegister.get();
        }
        return null;
    }

    @Override
    public Boolean validateMutant(MutantDnaValidateRequest request) {

        String idDna = UUID.nameUUIDFromBytes(request.toString().getBytes()).toString();

        MutantRegister foundRegister = getResultById(idDna);

        if(foundRegister != null){
            log.info("DNA Register Found on Database");
            return foundRegister.getIsMutant();
        }

        String [] dnaChain = request.getDna();
        char [][] dnaMatrix = new char[dnaChain.length][];

        for(int i=0;i<dnaChain.length;i++){
            dnaMatrix[i] = dnaChain[i].toCharArray();
        }
        Boolean isMutant = validateSecuences(dnaMatrix);

        if (isMutant){
            statsService.processStats(RegisterType.MUTANT);
        }else{
            statsService.processStats(RegisterType.HUMAN);
        }

        MutantRegister mutantRegister = new MutantRegister();
        mutantRegister.setIddna(idDna);
        mutantRegister.setDnaChain(Arrays.toString(dnaChain));
        mutantRegister.setIsMutant(isMutant);
        mutantDnaRepository.save(mutantRegister);
        return isMutant;
    }

    private Boolean validateSecuences(char [][] dnaMatrix){
        int secuence = 0;
        for(int i=0 ;i < dnaMatrix.length;i++){
            for(int j=0; j < dnaMatrix[i].length;j++){
                if(i+3<dnaMatrix[i].length && dnaMatrix[i][j] == dnaMatrix[i+3][j]){
                    if(dnaMatrix[i][j] == dnaMatrix[i+2][j]){
                        if(dnaMatrix[i][j] == dnaMatrix[i+1][j]){
                            secuence++;
                            log.info("Find Horizontal secuence");
                        }
                    }
                }
                if(j+3<dnaMatrix[i].length && dnaMatrix[i][j] == dnaMatrix[i][j+3]){
                    if(dnaMatrix[i][j] == dnaMatrix[i][j+2]){
                        if(dnaMatrix[i][j] == dnaMatrix[i][j+1]){
                            secuence++;
                            log.info("Find Vertical secuence");
                        }
                    }
                }
                if(i+3<dnaMatrix[i].length && j+3<dnaMatrix[i].length && dnaMatrix[i][j] == dnaMatrix[i+3][j+3]){
                    if(dnaMatrix[i][j] == dnaMatrix[i+2][j+2]){
                        if(dnaMatrix[i][j] == dnaMatrix[i+1][j+1]){
                            secuence++;
                            log.info("Find Oblique secuence Down");
                        }
                    }
                }
                if(i+3<dnaMatrix[i].length && j-3>=0 && dnaMatrix[i][j] == dnaMatrix[i+3][j-3]){
                    if(dnaMatrix[i][j] == dnaMatrix[i+2][j-2]){
                        if(dnaMatrix[i][j] == dnaMatrix[i+1][j-1]){
                            secuence++;
                            log.info("Find Oblique secuence Up");
                        }
                    }
                }
            }
        }
        if(secuence>1){
            return true;
        }
        return false;
    }


}
