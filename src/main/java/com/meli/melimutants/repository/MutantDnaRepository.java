package com.meli.melimutants.repository;

import com.meli.melimutants.model.MutantRegister;
import org.springframework.data.repository.CrudRepository;

public interface MutantDnaRepository extends CrudRepository<MutantRegister, String> {
}
