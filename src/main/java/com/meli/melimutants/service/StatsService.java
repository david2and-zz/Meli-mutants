package com.meli.melimutants.service;

import com.meli.melimutants.model.response.StatsResponse;
import com.meli.melimutants.utils.RegisterType;

public interface StatsService {
    StatsResponse getStats();
    void processStats(RegisterType registerType);
}
