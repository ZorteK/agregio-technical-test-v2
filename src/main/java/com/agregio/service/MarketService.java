package com.agregio.service;

import com.agregio.dto.request.MarketBlockRequest;
import com.agregio.dto.request.MarketBlockUpdateRequest;
import com.agregio.exception.NotFoundException;
import com.agregio.exception.ValidationException;
import com.agregio.model.MarketBlock;
import com.agregio.repository.MarketBlockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MarketService {
    private final MarketBlockRepository marketBlockRepository;
    private final ProductionService productionService;

    public Page<MarketBlock> list(MarketBlockRequest request, Pageable pageable) {
        Specification<MarketBlock> spec = getSpecification(request);
        return marketBlockRepository.findAll(spec, pageable);
    }

    private Specification<MarketBlock> getSpecification(MarketBlockRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("day"), request.getFrom()));
            }
            if (request.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("day"), request.getTo()));
            }
            if (request.getTypes() != null) {
                predicates.add(root.get("type").in(request.getTypes()));
            }
            if (request.getTypes() != null) {
                predicates.add(root.get("slot").in(request.getSlots()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public MarketBlock create(MarketBlock marketBlock) {
        marketBlockRepository.findByDayAndSlotAndType(marketBlock.getDay(), marketBlock.getSlot(), marketBlock.getType()).ifPresent(it -> {
            throw new ValidationException("MarketBlock already exists");
        });
        marketBlockRepository.save(marketBlock);
        return marketBlock;
    }

    public void patch(UUID id, MarketBlockUpdateRequest request) {
        MarketBlock marketBlock = marketBlockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found" + id));
        marketBlock.setPrice(request.getPrice());
        marketBlockRepository.save(marketBlock);
    }

    public void delete(UUID id) {
        MarketBlock marketBlock = marketBlockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MarketBlock entity not found with id: " + id));
        marketBlock.getProductions().forEach(productionService::delete);
        marketBlockRepository.delete(marketBlock);
    }

    public MarketBlock get(UUID id) {
        return marketBlockRepository.findById(id).orElseThrow(() -> new NotFoundException("MarketBlock entity not found with id: " + id));
    }
}
