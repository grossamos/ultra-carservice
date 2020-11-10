package com.nttdata.carservice.storage;

import com.nttdata.carservice.entity.Automobile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface AutomobileRepo extends CrudRepository<Automobile, Integer> {
}
