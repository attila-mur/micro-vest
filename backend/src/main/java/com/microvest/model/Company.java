package com.microvest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String city;

    private String country;

    private String logoEmoji;

    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String founderStatement;

    @Column(columnDefinition = "TEXT")
    private String investmentPlan;

    private int foundedYear;

    private int employeeCount;

    private double revenueLastYear;

    private double revenueYearBefore;

    private double fundingGoal;

    private double fundingRaised;

    private String riskLevel;

    private boolean featured;
}
