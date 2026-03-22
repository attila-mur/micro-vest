package com.microvest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDTO {

    private Long id;
    private String companyName;
    private Long companyId;
    private String logoEmoji;
    private String category;
    private double amountInvested;
    private double equityShareAcquired;
    private LocalDate investedAt;
    private String status;
}
