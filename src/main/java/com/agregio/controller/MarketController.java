package com.agregio.controller;

import com.agregio.converter.MarketMapper;
import com.agregio.dto.MarketBlockDetailDto;
import com.agregio.dto.MarketBlockDto;
import com.agregio.dto.request.MarketBlockCreateRequest;
import com.agregio.dto.request.MarketBlockUpdateRequest;
import com.agregio.dto.request.MarketBlockRequest;
import com.agregio.service.MarketService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@AllArgsConstructor
@Transactional
@Controller
public class MarketController {

    private final MarketService marketService;
    private final MarketMapper marketMapper;

    @PostMapping("marketPrices")
    public MarketBlockDto create(@RequestBody MarketBlockCreateRequest request) {
        return marketMapper.toDto(marketService.create( marketMapper.fromRequest(request)));
    }

    @GetMapping("marketPrices/{id}")
    public ResponseEntity<MarketBlockDetailDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(marketMapper.toDtoWithDetail(marketService.get(id)));
    }

    @PageableAsQueryParam
    @GetMapping("marketPrices")
    public ResponseEntity<Page<MarketBlockDto>> list(MarketBlockRequest request,  @PageableDefault(size = 10, sort = "day", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok().body(marketMapper.toDto(marketService.list(request, pageable)));
    }

    @PatchMapping("marketPrices/{id}")
    public ResponseEntity<Void> patch(@PathVariable UUID id, @RequestBody MarketBlockUpdateRequest request) {
        marketService.patch(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("marketPrices/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        marketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
