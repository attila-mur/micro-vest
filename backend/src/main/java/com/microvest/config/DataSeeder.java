package com.microvest.config;

import java.time.LocalDate;

import com.microvest.model.Company;
import com.microvest.model.Investment;
import com.microvest.repository.CompanyRepository;
import com.microvest.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements ApplicationRunner {

    private final CompanyRepository companyRepository;
    private final InvestmentRepository investmentRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding database...");
        seedCompanies();
        seedDemoPortfolio();
        log.info("Seeding complete. {} companies loaded.", companyRepository.count());
    }

    private void seedCompanies() {
        companyRepository.save(Company.builder()
                .name("The Golden Fork").category("Restaurant").city("Austin, TX").country("USA")
                .logoEmoji("\uD83C\uDF7D\uFE0F").tagline("Farm-to-table done right.")
                .description("Family-owned restaurant serving locally sourced dishes in the heart of Austin.")
                .founderStatement("We want our community to own a piece of what they helped us build.")
                .investmentPlan("Open a second location on South Congress Ave.")
                .foundedYear(2018).employeeCount(12).revenueLastYear(840000)
                .equityOffered(15).amountSought(150000).profitSharePercent(20)
                .riskLevel("Medium").featured(true).build());

        companyRepository.save(Company.builder()
                .name("Brew Brothers Craft Brewery").category("Brewery").city("Portland, OR").country("USA")
                .logoEmoji("\uD83C\uDF7A").tagline("Small batches. Big character.")
                .description("Award-winning microbrewery specializing in IPAs and seasonal ales.")
                .founderStatement("Every pint our investors drink supports the business they own.")
                .investmentPlan("Install new fermentation tanks and expand taproom seating.")
                .foundedYear(2016).employeeCount(9).revenueLastYear(1100000)
                .equityOffered(20).amountSought(200000).profitSharePercent(18)
                .riskLevel("Medium").featured(true).build());

        companyRepository.save(Company.builder()
                .name("Morning Bloom Cafe").category("Cafe").city("Nashville, TN").country("USA")
                .logoEmoji("\u2615").tagline("Where Nashville starts its day.")
                .description("Specialty coffee shop and brunch spot in East Nashville.")
                .founderStatement("Our regulars asked how they could support us beyond just buying coffee.")
                .investmentPlan("Launch an in-house roasting operation and online bean subscription.")
                .foundedYear(2020).employeeCount(8).revenueLastYear(520000)
                .equityOffered(12).amountSought(100000).profitSharePercent(22)
                .riskLevel("Low").featured(true).build());

        companyRepository.save(Company.builder()
                .name("Ember & Oak BBQ").category("Restaurant").city("Kansas City, MO").country("USA")
                .logoEmoji("\uD83D\uDD25").tagline("Low and slow. Worth the wait.")
                .description("Pitmaster-led BBQ joint with a loyal local following and growing catering business.")
                .founderStatement("We smoke brisket for 16 hours. Good things take time — including growth.")
                .investmentPlan("Build a dedicated catering kitchen and buy a food truck.")
                .foundedYear(2021).employeeCount(15).revenueLastYear(1350000)
                .equityOffered(10).amountSought(175000).profitSharePercent(15)
                .riskLevel("Low").featured(true).build());

        companyRepository.save(Company.builder()
                .name("La Bella Trattoria").category("Restaurant").city("Chicago, IL").country("USA")
                .logoEmoji("\uD83C\uDF5D").tagline("Nonna's recipes. Chicago's heart.")
                .description("Third-generation Italian restaurant in Lincoln Park.")
                .founderStatement("My grandmother started this. Now the neighborhood can own a part of it.")
                .investmentPlan("Renovate the dining room and add a private events space.")
                .foundedYear(2017).employeeCount(14).revenueLastYear(1180000)
                .equityOffered(8).amountSought(250000).profitSharePercent(15)
                .riskLevel("Low").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Hop Yard Brewing Co.").category("Brewery").city("Denver, CO").country("USA")
                .logoEmoji("\uD83C\uDF3F").tagline("Crafted at altitude. Enjoyed everywhere.")
                .description("Craft brewery focused on hop-forward beers with a growing distribution network.")
                .founderStatement("Our investors are our best brand ambassadors.")
                .investmentPlan("Expand distribution to 3 neighboring states.")
                .foundedYear(2019).employeeCount(16).revenueLastYear(1650000)
                .equityOffered(18).amountSought(300000).profitSharePercent(20)
                .riskLevel("Medium").featured(true).build());

        companyRepository.save(Company.builder()
                .name("The Cozy Bean Roasters").category("Cafe").city("Seattle, WA").country("USA")
                .logoEmoji("\u2615").tagline("Specialty coffee. Neighborhood soul.")
                .description("Single-origin coffee roaster with two cafe locations in Capitol Hill.")
                .founderStatement("We've always been community-driven. Now the community can share in our success.")
                .investmentPlan("Open a third location and launch wholesale to local restaurants.")
                .foundedYear(2017).employeeCount(7).revenueLastYear(620000)
                .equityOffered(14).amountSought(120000).profitSharePercent(18)
                .riskLevel("Low").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Verde Fresh Kitchen").category("Restaurant").city("Miami, FL").country("USA")
                .logoEmoji("\uD83E\uDD57").tagline("Fresh. Fast. Feel-good food.")
                .description("Fast-casual healthy restaurant with strong delivery and takeout business.")
                .founderStatement("Health food shouldn't be exclusive. Investing in us shouldn't be either.")
                .investmentPlan("Open two new locations in Brickell and Wynwood.")
                .foundedYear(2020).employeeCount(11).revenueLastYear(980000)
                .equityOffered(20).amountSought(350000).profitSharePercent(22)
                .riskLevel("Medium").featured(true).build());

        companyRepository.save(Company.builder()
                .name("Sunset Sushi & Sake").category("Restaurant").city("San Diego, CA").country("USA")
                .logoEmoji("\uD83C\uDF71").tagline("Pacific freshness. Japanese precision.")
                .description("Omakase-style sushi bar in the Gaslamp Quarter.")
                .founderStatement("Our craft demands the best ingredients. Community investment helps us source them.")
                .investmentPlan("Renovate kitchen and add a sake tasting room.")
                .foundedYear(2019).employeeCount(10).revenueLastYear(920000)
                .equityOffered(12).amountSought(200000).profitSharePercent(16)
                .riskLevel("Medium").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Sweet Surrender Bakery").category("Bakery").city("Brooklyn, NY").country("USA")
                .logoEmoji("\uD83E\uDDC1").tagline("Life is short. Eat the cake.")
                .description("Artisan bakery known for custom cakes and pastries in Park Slope.")
                .founderStatement("Every cake we sell is made with love. Every investment helps us share more.")
                .investmentPlan("Launch wholesale to local grocery stores and cafes.")
                .foundedYear(2016).employeeCount(8).revenueLastYear(680000)
                .equityOffered(10).amountSought(180000).profitSharePercent(17)
                .riskLevel("Low").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Mountain Goat Cheese Co.").category("Food Producer").city("Burlington, VT").country("USA")
                .logoEmoji("\uD83D\uDC10").tagline("Small herd. Big flavor.")
                .description("Artisanal goat cheese producer supplying farmers markets and local restaurants.")
                .founderStatement("We're small on purpose. Community ownership keeps it that way.")
                .investmentPlan("Build an aging cave and expand to regional distribution.")
                .foundedYear(2018).employeeCount(4).revenueLastYear(280000)
                .equityOffered(25).amountSought(85000).profitSharePercent(25)
                .riskLevel("Medium").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Neon Ramen House").category("Restaurant").city("Los Angeles, CA").country("USA")
                .logoEmoji("\uD83C\uDF5C").tagline("Bowls that glow. Broth with soul.")
                .description("Late-night ramen spot in Koreatown with a cult following.")
                .founderStatement("Our fans are already our biggest investors — emotionally. Now financially too.")
                .investmentPlan("Open a second location in Silver Lake.")
                .foundedYear(2020).employeeCount(13).revenueLastYear(1050000)
                .equityOffered(15).amountSought(225000).profitSharePercent(18)
                .riskLevel("Medium").featured(false).build());

        companyRepository.save(Company.builder()
                .name("The Urban Flower Shop").category("Retail").city("Atlanta, GA").country("USA")
                .logoEmoji("\uD83C\uDF38").tagline("Blooms that tell a story.")
                .description("Boutique florist specializing in events and subscriptions.")
                .founderStatement("Flowers connect people. So does shared ownership.")
                .investmentPlan("Launch a wedding venue partnership program and expand delivery radius.")
                .foundedYear(2019).employeeCount(6).revenueLastYear(480000)
                .equityOffered(18).amountSought(130000).profitSharePercent(20)
                .riskLevel("Medium").featured(false).build());

        companyRepository.save(Company.builder()
                .name("Artisan Soap & Candle Co.").category("Retail").city("Asheville, NC").country("USA")
                .logoEmoji("\uD83D\uDD6F\uFE0F").tagline("Handcrafted. Natural. Unforgettable.")
                .description("Small-batch soap and candle maker with a growing online presence.")
                .founderStatement("We started at farmers markets. Our customers helped us grow. Now they can invest.")
                .investmentPlan("Scale online sales and open a flagship retail store.")
                .foundedYear(2017).employeeCount(5).revenueLastYear(320000)
                .equityOffered(22).amountSought(75000).profitSharePercent(24)
                .riskLevel("Low").featured(false).build());

        companyRepository.save(Company.builder()
                .name("The Rusty Anchor Fish & Chips").category("Restaurant").city("Boston, MA").country("USA")
                .logoEmoji("\u2693").tagline("Dockside freshness since 2019.")
                .description("Waterfront fish & chips shop with the freshest catch in Boston Harbor.")
                .founderStatement("The harbor community made us. It's only right they share in what we've built.")
                .investmentPlan("Add outdoor seating and a lobster roll menu expansion.")
                .foundedYear(2019).employeeCount(11).revenueLastYear(890000)
                .equityOffered(12).amountSought(160000).profitSharePercent(19)
                .riskLevel("Low").featured(true).build());
    }

    private void seedDemoPortfolio() {
        var companies = companyRepository.findAll();

        // The Golden Fork — Active, 14 months ago
        investmentRepository.save(Investment.builder()
                .userId("demo-user").company(companies.get(0))
                .amountInvested(2500).equityShareAcquired(0.25)
                .investedAt(LocalDate.now().minusMonths(14)).status("Active").build());

        // Brew Brothers — Active, 8 months ago
        investmentRepository.save(Investment.builder()
                .userId("demo-user").company(companies.get(1))
                .amountInvested(5000).equityShareAcquired(0.50)
                .investedAt(LocalDate.now().minusMonths(8)).status("Active").build());

        // Verde Fresh Kitchen — Pending, just submitted
        investmentRepository.save(Investment.builder()
                .userId("demo-user").company(companies.get(7))
                .amountInvested(3000).equityShareAcquired(0.17)
                .investedAt(LocalDate.now()).status("Pending").build());

        // Ember & Oak BBQ — Active, 18 months ago (strong gains)
        investmentRepository.save(Investment.builder()
                .userId("demo-user").company(companies.get(3))
                .amountInvested(10000).equityShareAcquired(0.57)
                .investedAt(LocalDate.now().minusMonths(18)).status("Active").build());

        // Artisan Soap & Candle Co. — Active, 10 months ago
        investmentRepository.save(Investment.builder()
                .userId("demo-user").company(companies.get(13))
                .amountInvested(1000).equityShareAcquired(0.29)
                .investedAt(LocalDate.now().minusMonths(10)).status("Active").build());
    }
}
