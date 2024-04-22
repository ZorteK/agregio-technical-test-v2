package com.agregio.repository;

import com.agregio.model.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductionRepository extends JpaRepository<Production,UUID> {
}
