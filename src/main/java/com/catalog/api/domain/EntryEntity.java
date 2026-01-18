package com.catalog.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "entries")
@Data
@NoArgsConstructor
public class EntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubcategoryEntity subcategory;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
