package com.microvest.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.microvest.dto.InvestRequest;
import com.microvest.dto.InvestmentDTO;
import com.microvest.dto.PortfolioDTO;
import com.microvest.model.Company;
import com.microvest.model.Investment;
import com.microvest.model.InvestmentOption;
import com.microvest.repository.CompanyRepository;
import com.microvest.repository.InvestmentOptionRepository;
import com.microvest.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final InvestmentRepository investmentRepository;
    private final CompanyRepository companyRepository;
    private final InvestmentOptionRepository investmentOptionRepository;

    public PortfolioDTO getPortfolio(String userId) {
        log.info("Fetching portfolio for user: {}", userId);
        List<Investment> investments = investmentRepository.findByUserId(userId);

        List<InvestmentDTO> investmentDTOs = investments.stream()
                .map(this::toInvestmentDTO)
                .toList();

        double totalInvested = investmentDTOs.stream()
                .mapToDouble(InvestmentDTO::getAmountInvested)
                .sum();

        double estimatedValue = investmentDTOs.stream()
                .mapToDouble(InvestmentDTO::getEstimatedValue)
                .sum();

        double gainLossPercent = 0.0;
        if (totalInvested > 0) {
            gainLossPercent = Math.round(((estimatedValue - totalInvested) / totalInvested) * 100 * 10.0) / 10.0;
        }

        return PortfolioDTO.builder()
                .totalInvested(totalInvested)
                .estimatedValue(Math.round(estimatedValue * 100.0) / 100.0)
                .gainLossPercent(gainLossPercent)
                .investments(investmentDTOs)
                .build();
    }

    public InvestmentDTO invest(String userId, InvestRequest request) {
        log.info("Processing investment for user: {}, company: {}, amount: {}",
                userId, request.getCompanyId(), request.getAmount());

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        InvestmentOption option = investmentOptionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new RuntimeException("Investment option not found with id: " + request.getOptionId()));

        Investment investment = Investment.builder()
                .userId(userId)
                .company(company)
                .option(option)
                .amountInvested(request.getAmount())
                .investedAt(LocalDate.now())
                .status("Pending")
                .build();

        Investment saved = investmentRepository.save(investment);
        log.info("Investment created with id: {}", saved.getId());

        return toInvestmentDTO(saved);
    }

    private InvestmentDTO toInvestmentDTO(Investment investment) {
        Company company = investment.getCompany();
        InvestmentOption option = investment.getOption();

        double estimatedValue = calculateEstimatedValue(
                investment.getAmountInvested(),
                option.getExpectedAnnualReturn(),
                investment.getInvestedAt(),
                investment.getStatus()
        );

        return InvestmentDTO.builder()
                .id(investment.getId())
                .companyName(company.getName())
                .companyId(company.getId())
                .logoEmoji(company.getLogoEmoji())
                .category(company.getCategory())
                .amountInvested(investment.getAmountInvested())
                .estimatedValue(Math.round(estimatedValue * 100.0) / 100.0)
                .investedAt(investment.getInvestedAt())
                .status(investment.getStatus())
                .tier(option.getTier())
                .build();
    }

    private double calculateEstimatedValue(double amountInvested, double annualReturnPercent,
                                           LocalDate investedAt, String status) {
        if ("Pending".equals(status)) {
            return amountInvested;
        }
        if ("Exited".equals(status)) {
            long daysSinceInvestment = ChronoUnit.DAYS.between(investedAt, LocalDate.now());
            double years = daysSinceInvestment / 365.25;
            return amountInvested * Math.pow(1 + (annualReturnPercent / 100), years);
        }
        // Active
        long daysSinceInvestment = ChronoUnit.DAYS.between(investedAt, LocalDate.now());
        double years = daysSinceInvestment / 365.25;
        return amountInvested * Math.pow(1 + (annualReturnPercent / 100), years);
    }
}
