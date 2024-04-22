package com.agregio.repository;

import com.agregio.model.MarketBlock;
import com.agregio.model.Slot;
import com.agregio.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MarketBlockRepository extends JpaRepository<MarketBlock, UUID> {
    Page<MarketBlock> findAll(Specification<MarketBlock> spec, Pageable pageable);

    Optional<MarketBlock> findByDayAndSlotAndType(LocalDate date, Slot slot, Type type);
}
