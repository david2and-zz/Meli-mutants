package com.meli.melimutants.repository;

import com.meli.melimutants.model.StatsRegister;
import org.springframework.data.repository.CrudRepository;

public interface StatsRepository extends CrudRepository<StatsRegister, String> {
}
