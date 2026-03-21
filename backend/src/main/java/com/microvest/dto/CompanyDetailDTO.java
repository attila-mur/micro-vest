package com.microvest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailDTO {

    private Long id;
    private String name;
    private String category;
    private String city;
    private String logoEmoji;
    private String tagline;
    private int foundedYear;
    private int employeeCount;
    private double revenueLastYear;
    private double revenueGrowthPercent;
    private double fundingGoal;
    private double fundingRaised;
    private String riskLevel;
    private boolean featured;
    private String description;
    private String founderStatement;
    private String investmentPlan;
    private double revenueYearBefore;
    private List<InvestmentOptionDTO> investmentOptions;
}
