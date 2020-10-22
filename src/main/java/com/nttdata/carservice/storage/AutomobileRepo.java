package com.nttdata.carservice.storage;

import com.nttdata.carservice.entity.Automobile;
import org.springframework.data.repository.CrudRepository;

public interface AutomobileRepo extends CrudRepository<Automobile, Integer> {
}
