package com.agregio.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "market_block", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"day", "slot", "type"})
})
@NoArgsConstructor
@AllArgsConstructor
public class MarketBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "day")
    private LocalDate day;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot")
    private Slot slot;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "market_block_id")
    @Builder.Default
    private Set<Production> productions = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

}
