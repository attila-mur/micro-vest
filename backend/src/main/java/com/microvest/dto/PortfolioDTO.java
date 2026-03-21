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
public class PortfolioDTO {

    private double totalInvested;
    private double estimatedValue;
    private double gainLossPercent;
    private List<InvestmentDTO> investments;
}
