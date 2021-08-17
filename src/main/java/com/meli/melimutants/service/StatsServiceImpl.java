package com.meli.melimutants.service;

import com.meli.melimutants.model.StatsRegister;
import com.meli.melimutants.model.response.StatsResponse;
import com.meli.melimutants.repository.StatsRepository;
import com.meli.melimutants.utils.RegisterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService{

    private final StatsRepository statsRepository;
    private static final String UNIQUE_ID = "1";
    private StatsRegister findStats() {
        Optional<StatsRegister> statsRegister = statsRepository.findById(UNIQUE_ID);
        if (statsRegister.isPresent()) {
            return statsRegister.get();
        }
        return null;
    }

    @Override
    public StatsResponse getStats() {
        StatsRegister statsRegister = findStats();
        if (statsRegister == null){
            return new StatsResponse(0,0,0.0);
        }
        return new StatsResponse(statsRegister);
    }

    private void saveRegister(StatsRegister statsRegister){
        statsRepository.save(statsRegister);
    }

    @Override
    public void processStats(RegisterType registerType) {
        StatsRegister registerFound = findStats();
        if(registerFound == null){
            registerFound = new StatsRegister(UNIQUE_ID,0,0,0.0);
            if(registerType == RegisterType.HUMAN){
                registerFound.setHumanDnaValidated(1);
            }
            if(registerType == RegisterType.MUTANT){
                registerFound.setMutantDnaValidated(1);
            }
        }else{
            if(registerType == RegisterType.HUMAN){
                registerFound.setHumanDnaValidated(registerFound.getHumanDnaValidated()+1);
            }
            if(registerType == RegisterType.MUTANT){
                registerFound.setMutantDnaValidated(registerFound.getMutantDnaValidated()+1);
            }
        }
        if(registerFound.getHumanDnaValidated() == 0){
            registerFound.setRatio(1.0);
        }else{
            registerFound.setRatio( ((double)registerFound.getMutantDnaValidated()/(double)registerFound.getHumanDnaValidated()));
        }


        saveRegister(registerFound);
    }
}
