package com.meli.melimutants.service;

import com.meli.melimutants.model.StatsRegister;
import com.meli.melimutants.model.response.StatsResponse;
import com.meli.melimutants.repository.StatsRepository;
import com.meli.melimutants.utils.RegisterType;
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
public class StatsServiceTest {

    @InjectMocks
    private StatsServiceImpl statsService;

    @Mock
    private StatsRepository statsRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(statsService,"statsRepository",statsRepository);
    }

    @Test
    public void getStats(){
        Mockito.when(statsRepository.findById(Mockito.anyString())).thenReturn(this.getStatsRegister());
        StatsResponse response = statsService.getStats();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCount_human_dna(),(Integer) 40);
        Assert.assertEquals(response.getCount_mutant_dna(),(Integer) 100);
        Assert.assertEquals(response.getRatio(),(Double) 0.4);

    }

    @Test
    public void getStats_Empty(){
        Mockito.when(statsRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        StatsResponse response = statsService.getStats();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCount_human_dna(),(Integer) 0);
        Assert.assertEquals(response.getCount_mutant_dna(),(Integer) 0);
        Assert.assertEquals(response.getRatio(),(Double) 0.0);
    }

    @Test
    public void processStatsMutant(){
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(null);
        statsService.processStats(RegisterType.MUTANT);
        Mockito.verify(statsRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void processStatsHuman(){
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(null);
        statsService.processStats(RegisterType.HUMAN);
        Mockito.verify(statsRepository,Mockito.times(1)).save(Mockito.any());
    }
    private Optional<StatsRegister> getStatsRegister(){
        StatsRegister statsRegister = new StatsRegister();
        statsRegister.setRatio(0.4);
        statsRegister.setHumanDnaValidated(40);
        statsRegister.setMutantDnaValidated(100);
        statsRegister.setIdstats("1");
        return Optional.of(statsRegister);
    }
}
