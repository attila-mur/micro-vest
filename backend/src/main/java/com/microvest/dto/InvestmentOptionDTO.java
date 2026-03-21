package com.microvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentOptionDTO {

    private Long id;
    private String tier;
    private double minimumInvestment;
    private double expectedAnnualReturn;
    private int lockupMonths;
    private String perks;
}
