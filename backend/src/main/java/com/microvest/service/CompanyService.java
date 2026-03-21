package com.microvest.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.microvest.dto.CompanyDetailDTO;
import com.microvest.dto.CompanySummaryDTO;
import com.microvest.dto.InvestmentOptionDTO;
import com.microvest.model.Company;
import com.microvest.model.InvestmentOption;
import com.microvest.repository.CompanyRepository;
import com.microvest.repository.InvestmentOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final InvestmentOptionRepository investmentOptionRepository;

    public List<CompanySummaryDTO> getAllCompanies() {
        log.info("Fetching all companies");
        return companyRepository.findAll().stream()
                .map(this::toSummaryDTO)
                .toList();
    }

    public CompanyDetailDTO getCompanyById(Long id) {
        log.info("Fetching company with id: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        List<InvestmentOption> options = investmentOptionRepository.findByCompanyId(id);
        return toDetailDTO(company, options);
    }

    public byte[] generatePlStatement(Long companyId) {
        log.info("Generating P&L statement for company id: {}", companyId);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);

            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font fontItalic = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                float pageWidth = page.getMediaBox().getWidth();
                float margin = 60;
                float yPosition = 720;

                // Watermark
                content.saveGraphicsState();
                content.setNonStrokingColor(0.9f, 0.9f, 0.9f);
                content.beginText();
                content.setFont(fontBold, 48);
                content.newLineAtOffset(80, 400);
                content.showText("SAMPLE - NOT FOR");
                content.endText();
                content.beginText();
                content.setFont(fontBold, 48);
                content.newLineAtOffset(50, 340);
                content.showText("INVESTMENT DECISIONS");
                content.endText();
                content.restoreGraphicsState();

                // Header
                content.beginText();
                content.setFont(fontBold, 22);
                content.newLineAtOffset(margin, yPosition);
                content.showText(company.getName());
                content.endText();

                yPosition -= 25;
                content.beginText();
                content.setFont(fontRegular, 12);
                content.newLineAtOffset(margin, yPosition);
                content.showText("Profit & Loss Statement");
                content.endText();

                yPosition -= 18;
                int currentYear = LocalDate.now().getYear();
                content.beginText();
                content.setFont(fontRegular, 10);
                content.newLineAtOffset(margin, yPosition);
                content.showText("For the fiscal year ending December 31, " + (currentYear - 1));
                content.endText();

                yPosition -= 12;
                content.beginText();
                content.setFont(fontItalic, 9);
                content.newLineAtOffset(margin, yPosition);
                content.showText(company.getCity() + ", " + company.getCountry() + "  |  " + company.getCategory());
                content.endText();

                // Divider line
                yPosition -= 15;
                content.setLineWidth(1f);
                content.moveTo(margin, yPosition);
                content.lineTo(pageWidth - margin, yPosition);
                content.stroke();

                // Revenue section
                double revenue = company.getRevenueLastYear();
                double prevRevenue = company.getRevenueYearBefore();
                double cogs = revenue * 0.38;
                double grossProfit = revenue - cogs;
                double wages = revenue * 0.28;
                double rent = revenue * 0.10;
                double utilities = revenue * 0.03;
                double marketing = revenue * 0.05;
                double insurance = revenue * 0.02;
                double depreciation = revenue * 0.03;
                double otherExpenses = revenue * 0.04;
                double totalOpex = wages + rent + utilities + marketing + insurance + depreciation + otherExpenses;
                double operatingIncome = grossProfit - totalOpex;
                double taxes = Math.max(operatingIncome * 0.22, 0);
                double netIncome = operatingIncome - taxes;

                yPosition -= 30;
                yPosition = drawSectionHeader(content, "Revenue", margin, yPosition, fontBold);
                yPosition = drawLineItem(content, "Net Sales Revenue", revenue, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Prior Year Revenue", prevRevenue, margin, yPosition, fontRegular, pageWidth);
                double growthPct = ((revenue - prevRevenue) / prevRevenue) * 100;
                yPosition -= 5;
                content.beginText();
                content.setFont(fontItalic, 9);
                content.newLineAtOffset(margin + 10, yPosition);
                content.showText(String.format("Year-over-year growth: %.1f%%", growthPct));
                content.endText();

                yPosition -= 25;
                yPosition = drawSectionHeader(content, "Cost of Goods Sold", margin, yPosition, fontBold);
                yPosition = drawLineItem(content, "Cost of Goods Sold", cogs, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawTotalLine(content, "Gross Profit", grossProfit, margin, yPosition, fontBold, pageWidth);

                yPosition -= 20;
                yPosition = drawSectionHeader(content, "Operating Expenses", margin, yPosition, fontBold);
                yPosition = drawLineItem(content, "Wages & Salaries", wages, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Rent & Occupancy", rent, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Utilities", utilities, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Marketing & Advertising", marketing, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Insurance", insurance, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Depreciation", depreciation, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawLineItem(content, "Other Operating Expenses", otherExpenses, margin, yPosition, fontRegular, pageWidth);
                yPosition = drawTotalLine(content, "Total Operating Expenses", totalOpex, margin, yPosition, fontBold, pageWidth);

                yPosition -= 15;
                yPosition = drawTotalLine(content, "Operating Income", operatingIncome, margin, yPosition, fontBold, pageWidth);
                yPosition -= 5;
                yPosition = drawLineItem(content, "Income Tax Provision (22%)", taxes, margin, yPosition, fontRegular, pageWidth);

                // Net Income - highlighted
                yPosition -= 10;
                content.setLineWidth(1.5f);
                content.moveTo(margin, yPosition + 5);
                content.lineTo(pageWidth - margin, yPosition + 5);
                content.stroke();
                yPosition = drawTotalLine(content, "Net Income", netIncome, margin, yPosition, fontBold, pageWidth);
                content.setLineWidth(1.5f);
                content.moveTo(margin, yPosition + 2);
                content.lineTo(pageWidth - margin, yPosition + 2);
                content.stroke();

                // Footer disclaimer
                yPosition = 80;
                content.setLineWidth(0.5f);
                content.moveTo(margin, yPosition + 10);
                content.lineTo(pageWidth - margin, yPosition + 10);
                content.stroke();

                content.beginText();
                content.setFont(fontItalic, 7);
                content.newLineAtOffset(margin, yPosition);
                content.showText("This is a sample document generated by MicroVest for demonstration purposes only.");
                content.endText();
                yPosition -= 10;
                content.beginText();
                content.setFont(fontItalic, 7);
                content.newLineAtOffset(margin, yPosition);
                content.showText("Figures are illustrative and should not be used for actual investment decisions.");
                content.endText();
                yPosition -= 10;
                content.beginText();
                content.setFont(fontItalic, 7);
                content.newLineAtOffset(margin, yPosition);
                content.showText("Generated on " + LocalDate.now() + "  |  MicroVest - Invest in the places you love.");
                content.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("Error generating P&L statement for company id: {}", companyId, e);
            throw new RuntimeException("Failed to generate P&L statement", e);
        }
    }

    private float drawSectionHeader(PDPageContentStream content, String title, float margin, float y,
                                    PDType1Font font) throws IOException {
        content.beginText();
        content.setFont(font, 11);
        content.newLineAtOffset(margin, y);
        content.showText(title);
        content.endText();
        return y - 18;
    }

    private float drawLineItem(PDPageContentStream content, String label, double amount, float margin, float y,
                               PDType1Font font, float pageWidth) throws IOException {
        content.beginText();
        content.setFont(font, 10);
        content.newLineAtOffset(margin + 10, y);
        content.showText(label);
        content.endText();

        String amountStr = String.format("$%,.0f", amount);
        float textWidth = font.getStringWidth(amountStr) / 1000 * 10;
        content.beginText();
        content.setFont(font, 10);
        content.newLineAtOffset(pageWidth - margin - textWidth, y);
        content.showText(amountStr);
        content.endText();

        return y - 16;
    }

    private float drawTotalLine(PDPageContentStream content, String label, double amount, float margin, float y,
                                PDType1Font font, float pageWidth) throws IOException {
        y -= 5;
        content.beginText();
        content.setFont(font, 11);
        content.newLineAtOffset(margin + 10, y);
        content.showText(label);
        content.endText();

        String amountStr = String.format("$%,.0f", amount);
        float textWidth = font.getStringWidth(amountStr) / 1000 * 11;
        content.beginText();
        content.setFont(font, 11);
        content.newLineAtOffset(pageWidth - margin - textWidth, y);
        content.showText(amountStr);
        content.endText();

        return y - 18;
    }

    private CompanySummaryDTO toSummaryDTO(Company company) {
        double growthPercent = 0.0;
        if (company.getRevenueYearBefore() > 0) {
            growthPercent = ((company.getRevenueLastYear() - company.getRevenueYearBefore())
                    / company.getRevenueYearBefore()) * 100;
        }
        return CompanySummaryDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .category(company.getCategory())
                .city(company.getCity())
                .logoEmoji(company.getLogoEmoji())
                .tagline(company.getTagline())
                .foundedYear(company.getFoundedYear())
                .employeeCount(company.getEmployeeCount())
                .revenueLastYear(company.getRevenueLastYear())
                .revenueGrowthPercent(Math.round(growthPercent * 10.0) / 10.0)
                .fundingGoal(company.getFundingGoal())
                .fundingRaised(company.getFundingRaised())
                .riskLevel(company.getRiskLevel())
                .featured(company.isFeatured())
                .build();
    }

    private CompanyDetailDTO toDetailDTO(Company company, List<InvestmentOption> options) {
        double growthPercent = 0.0;
        if (company.getRevenueYearBefore() > 0) {
            growthPercent = ((company.getRevenueLastYear() - company.getRevenueYearBefore())
                    / company.getRevenueYearBefore()) * 100;
        }
        List<InvestmentOptionDTO> optionDTOs = options.stream()
                .map(opt -> InvestmentOptionDTO.builder()
                        .id(opt.getId())
                        .tier(opt.getTier())
                        .minimumInvestment(opt.getMinimumInvestment())
                        .expectedAnnualReturn(opt.getExpectedAnnualReturn())
                        .lockupMonths(opt.getLockupMonths())
                        .perks(opt.getPerks())
                        .build())
                .toList();

        return CompanyDetailDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .category(company.getCategory())
                .city(company.getCity())
                .logoEmoji(company.getLogoEmoji())
                .tagline(company.getTagline())
                .foundedYear(company.getFoundedYear())
                .employeeCount(company.getEmployeeCount())
                .revenueLastYear(company.getRevenueLastYear())
                .revenueGrowthPercent(Math.round(growthPercent * 10.0) / 10.0)
                .fundingGoal(company.getFundingGoal())
                .fundingRaised(company.getFundingRaised())
                .riskLevel(company.getRiskLevel())
                .featured(company.isFeatured())
                .description(company.getDescription())
                .founderStatement(company.getFounderStatement())
                .investmentPlan(company.getInvestmentPlan())
                .revenueYearBefore(company.getRevenueYearBefore())
                .investmentOptions(optionDTOs)
                .build();
    }
}
