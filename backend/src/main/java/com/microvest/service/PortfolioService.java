package com.microvest.service;

import java.time.LocalDate;
import java.util.List;

import com.microvest.dto.InvestRequest;
import com.microvest.dto.InvestmentDTO;
import com.microvest.dto.PortfolioDTO;
import com.microvest.model.Company;
import com.microvest.model.Investment;
import com.microvest.repository.CompanyRepository;
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

    public PortfolioDTO getPortfolio(String userId) {
        log.info("Fetching portfolio for user: {}", userId);
        List<Investment> investments = investmentRepository.findByUserId(userId);

        List<InvestmentDTO> investmentDTOs = investments.stream()
                .map(this::toInvestmentDTO)
                .toList();

        double totalInvested = investmentDTOs.stream()
                .mapToDouble(InvestmentDTO::getAmountInvested)
                .sum();

        return PortfolioDTO.builder()
                .totalInvested(totalInvested)
                .investments(investmentDTOs)
                .build();
    }

    public InvestmentDTO invest(String userId, InvestRequest request) {
        log.info("Processing investment for user: {}, company: {}, amount: {}",
                userId, request.getCompanyId(), request.getAmount());

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        double equityShare = (request.getAmount() / company.getAmountSought()) * company.getEquityOffered();

        Investment investment = Investment.builder()
                .userId(userId)
                .company(company)
                .amountInvested(request.getAmount())
                .equityShareAcquired(Math.round(equityShare * 100.0) / 100.0)
                .investedAt(LocalDate.now())
                .status("Pending")
                .build();

        Investment saved = investmentRepository.save(investment);
        log.info("Investment created with id: {}", saved.getId());

        return toInvestmentDTO(saved);
    }

    private InvestmentDTO toInvestmentDTO(Investment investment) {
        Company company = investment.getCompany();

        return InvestmentDTO.builder()
                .id(investment.getId())
                .companyName(company.getName())
                .companyId(company.getId())
                .logoEmoji(company.getLogoEmoji())
                .category(company.getCategory())
                .amountInvested(investment.getAmountInvested())
                .equityShareAcquired(investment.getEquityShareAcquired())
                .investedAt(investment.getInvestedAt())
                .status(investment.getStatus())
                .build();
    }
}
