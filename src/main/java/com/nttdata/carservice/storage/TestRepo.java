package com.nttdata.carservice.storage;

import com.nttdata.carservice.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepo extends CrudRepository<TestEntity, Integer> {
}
