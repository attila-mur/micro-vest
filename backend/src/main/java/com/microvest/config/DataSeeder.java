package com.microvest.config;

import java.time.LocalDate;
import java.util.List;

import com.microvest.model.Company;
import com.microvest.model.Investment;
import com.microvest.model.InvestmentOption;
import com.microvest.repository.CompanyRepository;
import com.microvest.repository.InvestmentOptionRepository;
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
    private final InvestmentOptionRepository investmentOptionRepository;
    private final InvestmentRepository investmentRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding database with companies, investment options, and demo portfolio...");
        seedCompanies();
        log.info("Database seeding complete.");
    }

    private void seedCompanies() {
        // ──────────────────────────────────────────────────
        // 1. The Golden Fork
        // ──────────────────────────────────────────────────
        Company goldenFork = companyRepository.save(Company.builder()
                .name("The Golden Fork")
                .category("Restaurant")
                .city("Austin, TX")
                .country("USA")
                .logoEmoji("\uD83C\uDF7D\uFE0F")
                .tagline("Farm-to-table done right.")
                .description("The Golden Fork started in 2018 as a 28-seat pop-up inside a converted warehouse on East Sixth Street. Chef and founder Maria Gutierrez had spent a decade cooking at Michelin-starred restaurants in New York before returning to her hometown of Austin with a mission: serve world-class food made exclusively with ingredients sourced within 150 miles of the restaurant.\n\nToday The Golden Fork seats 85 guests across a sun-drenched dining room and a live-oak-shaded patio. The menu changes weekly based on what local ranchers, fishers, and organic farms bring in. Signature dishes like the pecan-smoked brisket tacos and lavender cr\u00e8me br\u00fbl\u00e9e have earned coverage in Bon App\u00e9tit and Texas Monthly. Revenue has grown every single year since opening, and 2024 was the strongest yet.\n\nThe Golden Fork employs twelve full-time staff and partners with six family farms. The restaurant has become a gathering place for the East Austin community, hosting weekly live-music brunches and quarterly farm-to-fork dinners where diners meet the farmers behind their food.")
                .founderStatement("I came back to Austin because I believe fine dining shouldn't require a flight to Manhattan. Every plate we serve tells the story of a Texas farmer, rancher, or forager. When you invest in The Golden Fork, you're investing in a food system that keeps money circulating in our community rather than flowing to distant supply chains. I want to open a second location on South Congress and bring this experience to even more Austinites.")
                .investmentPlan("Funds will be used to secure a lease and build out a second 60-seat location on South Congress Avenue. Approximately $80,000 will go toward kitchen equipment and interior design, $40,000 toward initial inventory and staffing for the first three months, and $30,000 toward marketing the grand opening including a partnership with Austin Food & Wine Festival.")
                .foundedYear(2018)
                .employeeCount(12)
                .revenueLastYear(840000)
                .revenueYearBefore(708000)
                .fundingGoal(150000)
                .fundingRaised(67000)
                .riskLevel("Medium")
                .featured(true)
                .build());

        createOptions(goldenFork, List.of(
                option("Seed", 500, 8.5, 12, "10% dining discount for the life of your investment, plus an invitation to the annual Harvest Dinner"),
                option("Growth", 2500, 11.0, 24, "15% dining discount, reserved priority seating on weekends, quarterly investor tasting event"),
                option("Partner", 10000, 14.0, 36, "20% dining discount, name on the Founders Wall at the new location, private dining experience for 8 guests annually")
        ));

        // ──────────────────────────────────────────────────
        // 2. Brew Brothers Craft Brewery
        // ──────────────────────────────────────────────────
        Company brewBrothers = companyRepository.save(Company.builder()
                .name("Brew Brothers Craft Brewery")
                .category("Brewery")
                .city("Portland, OR")
                .country("USA")
                .logoEmoji("\uD83C\uDF7A")
                .tagline("Small batches. Big character.")
                .description("Brew Brothers was born in 2016 when siblings Jake and Lena Kowalski converted their grandfather's auto-body shop in Portland's Alberta Arts District into a ten-barrel brewhouse. Starting with just three flagship beers, they quickly built a cult following for their hazy IPAs and barrel-aged stouts. Today the taproom pours fourteen rotating taps and distributes kegs to over forty bars and restaurants across Oregon and Washington.\n\nThe brewery's ethos is rooted in experimentation. Every month Brew Brothers releases a limited \"Garage Series\" beer brewed with unusual local ingredients -- Willamette Valley marionberries, cold-brewed Stumptown coffee, Hood River pears. These small-batch releases regularly sell out within hours and have earned Brew Brothers three consecutive medals at the Great American Beer Festival.\n\nWith taproom revenue surging and wholesale demand outpacing production capacity, the Kowalskis are ready to install a new 30-barrel system that will triple output without sacrificing the hands-on craftsmanship their fans love.")
                .founderStatement("Our grandfather built things with his hands in this very building for forty years. Lena and I are doing the same thing, just with malt and hops instead of metal and paint. We've proven the demand is there. Now we need the equipment to match it. Every investor becomes part of the Brew Brothers family -- we'll literally put your name on a brick in our taproom expansion wall.")
                .investmentPlan("The $200,000 raise will fund a 30-barrel Ss Brewtech brewing system ($120,000), four additional 60-barrel fermentation tanks ($45,000), and a taproom expansion adding 40 seats with a new outdoor beer garden ($35,000). This will allow Brew Brothers to triple production volume and enter the Oregon grocery retail channel through a partnership with New Seasons Market.")
                .foundedYear(2016)
                .employeeCount(9)
                .revenueLastYear(1100000)
                .revenueYearBefore(880000)
                .fundingGoal(200000)
                .fundingRaised(112000)
                .riskLevel("Medium")
                .featured(true)
                .build());

        createOptions(brewBrothers, List.of(
                option("Seed", 250, 7.5, 12, "Free taproom pint on every visit, early access to Garage Series releases"),
                option("Growth", 2000, 10.0, 24, "Monthly mixed six-pack shipped to your door, 20% taproom discount, brewery tour for up to 6 guests"),
                option("Partner", 7500, 13.5, 36, "All Growth perks plus a custom beer brewed to your taste, name on the taproom expansion wall, annual brew day experience")
        ));

        // ──────────────────────────────────────────────────
        // 3. Morning Bloom Caf\u00e9
        // ──────────────────────────────────────────────────
        Company morningBloom = companyRepository.save(Company.builder()
                .name("Morning Bloom Caf\u00e9")
                .category("Caf\u00e9")
                .city("Nashville, TN")
                .country("USA")
                .logoEmoji("\u2615")
                .tagline("Where Nashville starts its day.")
                .description("Morning Bloom Caf\u00e9 opened in Nashville's Germantown neighborhood in 2020, just weeks before the world changed. Founder Ayesha Williams pivoted overnight to a walk-up window model, and the community rallied around her single-origin pour-overs and housemade pastries. What started as a survival strategy became the caf\u00e9's signature: a breezy, open-air experience with a flower-filled courtyard that feels like a secret garden in the middle of the city.\n\nToday Morning Bloom is one of Nashville's most-Instagrammed coffee spots, drawing both locals and tourists from the nearby Ryman Auditorium and Broadway corridor. Ayesha sources beans directly from women-owned cooperatives in Ethiopia and Colombia, roasting on-site in a vintage Probat roaster. The food menu has expanded to include full brunch service on weekends, featuring Tennessee-raised eggs, locally milled grits, and honeycomb from a rooftop apiary.\n\nDespite opening during a pandemic, Morning Bloom has been profitable every year. Revenue grew 22% last year as the brunch program hit its stride and wholesale bean sales launched through the caf\u00e9's online store.")
                .founderStatement("I opened Morning Bloom because I wanted to create a space that felt like a warm hug first thing in the morning. Coffee is personal -- it's the first thing you reach for, the ritual that sets the tone for your day. Every bean we roast tells a story of a woman farmer who poured her heart into growing it. With your investment, I want to scale our roasting operation so we can share that story with coffee lovers beyond Nashville.")
                .investmentPlan("Morning Bloom is raising $100,000 to expand its roasting capacity and launch regional wholesale distribution. $55,000 will go toward a new 15-kilo Loring roaster to replace the current 5-kilo Probat, $25,000 toward packaging and branding for retail bags, and $20,000 toward an e-commerce platform and initial digital marketing campaign to drive online bean sales.")
                .foundedYear(2020)
                .employeeCount(8)
                .revenueLastYear(520000)
                .revenueYearBefore(426000)
                .fundingGoal(100000)
                .fundingRaised(41000)
                .riskLevel("Low")
                .featured(true)
                .build());

        createOptions(morningBloom, List.of(
                option("Seed", 250, 7.0, 12, "Free drip coffee on every visit, 10% off all retail bean purchases"),
                option("Growth", 1500, 9.5, 18, "Monthly bag of fresh-roasted beans delivered to your home, 15% caf\u00e9 discount, invitation to seasonal cupping events"),
                option("Partner", 5000, 12.0, 24, "All Growth perks plus a private latte art class for you and 5 friends, name featured on the caf\u00e9's Founder Friends wall, early access to limited roasts")
        ));

        // ──────────────────────────────────────────────────
        // 4. Ember & Oak BBQ
        // ──────────────────────────────────────────────────
        Company emberOak = companyRepository.save(Company.builder()
                .name("Ember & Oak BBQ")
                .category("Restaurant")
                .city("Kansas City, MO")
                .country("USA")
                .logoEmoji("\uD83D\uDD25")
                .tagline("Low and slow. Worth the wait.")
                .description("In a city that takes barbecue as seriously as religion, Ember & Oak has earned a place among Kansas City's finest pitmasters in just four years. Founder and pitmaster Darnell Washington grew up tending his grandmother's smoker in the backyard of her Troost Avenue home. After fifteen years in corporate finance, he traded spreadsheets for smoke rings, perfecting his craft at weekend pop-ups before opening a brick-and-mortar location in the Crossroads District in 2021.\n\nEmber & Oak's menu honors Kansas City tradition while pushing boundaries. The burnt ends are legendary -- customers line up before dawn on Saturdays -- but it's the unexpected items like smoked lamb ribs with pomegranate glaze and charcoal-grilled oysters with hot honey butter that set the restaurant apart. The restaurant seats 55 inside and another 30 on a covered patio with views of the downtown skyline.\n\nEmber & Oak was named one of Kansas City Star's \"Top 10 New Restaurants\" in 2022 and has maintained a 4.8-star rating across 2,400 Google reviews. Revenue jumped 28% last year as the catering business took off, serving corporate events, weddings, and Chiefs game-day tailgate packages.")
                .founderStatement("My grandmother always said the secret to great barbecue is patience and love. I left a six-figure salary because I realized I was spending my patience on quarterly reports instead of briskets. This restaurant is my love letter to Kansas City's BBQ tradition and to Grandma Lorraine. The investment will help me build a dedicated catering kitchen so we can serve even more people without taking away from the dine-in experience that started it all.")
                .investmentPlan("Ember & Oak is raising $175,000 to build a dedicated off-site catering prep kitchen and purchase a custom-built 1,000-gallon offset smoker trailer. $95,000 goes toward the kitchen build-out and lease, $50,000 for the smoker trailer and a refrigerated delivery van, and $30,000 for hiring two additional pitmasters and a catering coordinator.")
                .foundedYear(2021)
                .employeeCount(15)
                .revenueLastYear(1350000)
                .revenueYearBefore(1055000)
                .fundingGoal(175000)
                .fundingRaised(98000)
                .riskLevel("Low")
                .featured(true)
                .build());

        createOptions(emberOak, List.of(
                option("Seed", 500, 8.0, 12, "Priority line pass (skip the Saturday wait), 10% dining discount"),
                option("Growth", 3000, 11.0, 24, "Monthly sampler box of smoked meats and sauces, 15% discount, VIP seating at the annual Smoke & Soul Festival"),
                option("Partner", 10000, 14.5, 36, "All Growth perks plus a private BBQ masterclass with Darnell for up to 10 guests, name on the Pitmaster Wall, free catering for one event per year (up to 25 guests)")
        ));

        // ──────────────────────────────────────────────────
        // 5. La Bella Trattoria
        // ──────────────────────────────────────────────────
        Company laBella = companyRepository.save(Company.builder()
                .name("La Bella Trattoria")
                .category("Restaurant")
                .city("Chicago, IL")
                .country("USA")
                .logoEmoji("\uD83C\uDF5D")
                .tagline("Nonna's recipes. Chicago's heart.")
                .description("La Bella Trattoria has been a fixture of Chicago's Taylor Street since 2017, when Lucia Ferraro left her career as an architect to fulfill a promise she made to her late grandmother: bring Nonna Rosa's recipes from their village in Calabria to America. The 45-seat trattoria is tucked into a century-old brownstone with exposed brick walls, hand-painted Italian tiles, and the intoxicating aroma of fresh pasta being rolled in the open kitchen.\n\nEvery noodle at La Bella is made by hand, every sauce simmers for hours, and the olive oil is imported directly from Nonna Rosa's cousin's grove in Cosenza. The menu changes seasonally but always features the crowd favorites: tagliatelle al rag\u00f9, eggplant parmigiana, and a tiramis\u00f9 so authentic it once made an Italian food critic cry. The restaurant has earned a Michelin Bib Gourmand two years running.\n\nLa Bella has cultivated a fiercely loyal neighborhood following while also drawing foodies from across the city. The waitlist on Friday and Saturday nights regularly exceeds two hours, and the restaurant has had to turn away dozens of catering requests due to kitchen capacity constraints.")
                .founderStatement("Nonna Rosa spent her life feeding people with love. She never wrote her recipes down -- I learned them by standing beside her, watching her hands move. When she passed, I knew I had to preserve what she taught me. La Bella is her kitchen, transported across an ocean. With this investment, I want to expand our kitchen so we can finally say yes to the catering requests, launch a fresh pasta subscription, and maybe, just maybe, open a second trattoria in Wicker Park.")
                .investmentPlan("La Bella Trattoria is raising $250,000 for three initiatives: a kitchen expansion and renovation ($130,000) that will double prep space and add a dedicated pasta production line, a fresh pasta subscription service ($50,000 for packaging equipment, cold-chain logistics, and marketing), and initial planning and lease negotiations for a second location in Wicker Park ($70,000 for architectural plans, legal fees, and a lease deposit).")
                .foundedYear(2017)
                .employeeCount(14)
                .revenueLastYear(1180000)
                .revenueYearBefore(980000)
                .fundingGoal(250000)
                .fundingRaised(163000)
                .riskLevel("Low")
                .featured(false)
                .build());

        createOptions(laBella, List.of(
                option("Seed", 500, 7.5, 12, "Complimentary appetizer on every visit, 10% dining discount, birthday tiramis\u00f9"),
                option("Growth", 3000, 10.5, 24, "Monthly fresh pasta delivery (serves 4), 15% discount, reserved table for Saturday dinner once per month"),
                option("Partner", 12000, 13.0, 36, "All Growth perks plus a private pasta-making class with Lucia for 8 guests, name engraved on the Nonna Rosa memorial plaque, complimentary catering for one event per year (up to 20 guests)")
        ));

        // ──────────────────────────────────────────────────
        // 6. Hop Yard Brewing Co.
        // ──────────────────────────────────────────────────
        Company hopYard = companyRepository.save(Company.builder()
                .name("Hop Yard Brewing Co.")
                .category("Brewery")
                .city("Denver, CO")
                .country("USA")
                .logoEmoji("\uD83C\uDF3F")
                .tagline("Crafted at altitude. Enjoyed everywhere.")
                .description("Hop Yard Brewing Co. was founded in 2019 by former microbiologist Dr. Sam Okafor in Denver's RiNo Art District. Sam spent three years perfecting his recipes in a garage before opening a 5,000-square-foot brewery and taproom in a converted railway depot. The space features soaring ceilings, murals by local artists, and a 200-seat outdoor biergarten that has become one of Denver's favorite summer hangouts.\n\nWhat sets Hop Yard apart is Sam's scientific approach to brewing. Each batch is meticulously tracked with lab-grade instruments, resulting in a consistency that's rare in craft beer. The flagship \"Mile High Haze\" IPA has become the best-selling craft beer at Denver International Airport, and the \"Rocky Mountain Pilsner\" was voted Colorado's Best Lager by Westword magazine in 2024.\n\nHop Yard currently distributes across Colorado and recently signed deals to enter the New Mexico and Wyoming markets. The taproom alone accounts for 40% of revenue, with food truck partnerships and weekly trivia nights keeping the biergarten packed even on weeknights.")
                .founderStatement("I traded a microscope for a mash tun, and I've never looked back. Brewing is applied science with a soul -- you need precision to make great beer, but you need passion to make beer that people remember. Hop Yard is where science meets community. We've outgrown our current fermentation capacity, and this raise will let us meet the wholesale demand that's been knocking at our door from neighboring states.")
                .investmentPlan("Hop Yard is raising $300,000 to scale production and expand distribution. $160,000 will fund six new 60-barrel fermentation tanks and a centrifuge for cold-side processing, $80,000 will go toward a canning line to enable retail packaging (currently keg-only), and $60,000 will cover distribution logistics including a refrigerated truck, sales staff for the New Mexico and Wyoming markets, and trade show attendance at the Great American Beer Festival.")
                .foundedYear(2019)
                .employeeCount(16)
                .revenueLastYear(1650000)
                .revenueYearBefore(1320000)
                .fundingGoal(300000)
                .fundingRaised(189000)
                .riskLevel("Medium")
                .featured(true)
                .build());

        createOptions(hopYard, List.of(
                option("Seed", 500, 8.0, 12, "Two free pints per taproom visit, early access to seasonal releases, 10% off merchandise"),
                option("Growth", 3000, 11.0, 24, "Monthly case of mixed beers shipped to your door, 20% taproom discount, VIP access to the annual Hop Yard Fest"),
                option("Partner", 10000, 14.0, 36, "All Growth perks plus a private brewing session with Dr. Sam, custom beer label with your name, lifetime taproom membership")
        ));

        // ──────────────────────────────────────────────────
        // 7. The Cozy Bean Roasters
        // ──────────────────────────────────────────────────
        Company cozyBean = companyRepository.save(Company.builder()
                .name("The Cozy Bean Roasters")
                .category("Caf\u00e9")
                .city("Seattle, WA")
                .country("USA")
                .logoEmoji("\u2615")
                .tagline("Specialty coffee. Neighborhood soul.")
                .description("In a city dominated by coffee giants, The Cozy Bean Roasters has carved out a devoted following by doing things differently. Founded in 2017 by husband-and-wife team Marcus and Jin Chen in Seattle's Ballard neighborhood, the caf\u00e9 started as a tiny 12-seat space with a focus on single-origin beans and meticulous hand-brewing methods. Marcus handles the roasting -- he's a certified Q Grader, one of fewer than 5,000 worldwide -- while Jin runs front-of-house and manages the wholesale accounts.\n\nThe Cozy Bean now operates from a beautifully renovated 1920s storefront with a roasting facility visible through a glass wall. Customers can watch their beans being roasted in real time while sipping a perfectly extracted V60 or a creamy oat-milk cortado. The caf\u00e9 sources directly from farms in Kenya, Guatemala, and Sumatra, paying well above Fair Trade minimums.\n\nWholesale has become the fastest-growing segment of the business, with over 30 Seattle-area restaurants and offices now serving Cozy Bean coffee. Revenue crossed the $600,000 mark last year, and the couple is exploring opening a second roastery-caf\u00e9 in Capitol Hill to meet demand.")
                .founderStatement("Seattle doesn't need another coffee shop -- but it needs better coffee relationships. We visit every farm we buy from, and every bag we roast carries the farmer's name and story. Jin and I built The Cozy Bean to prove that you can run a profitable coffee business while paying farmers what they deserve. This investment will help us double down on that mission with a second location and expanded wholesale capacity.")
                .investmentPlan("The $120,000 raise will fund the build-out of a second Cozy Bean location in Capitol Hill ($70,000 for lease deposit, renovation, and equipment), an upgrade to a 25-kilo Giesen roaster to support growing wholesale demand ($35,000), and the development of a coffee subscription e-commerce platform ($15,000).")
                .foundedYear(2017)
                .employeeCount(7)
                .revenueLastYear(620000)
                .revenueYearBefore(530000)
                .fundingGoal(120000)
                .fundingRaised(54000)
                .riskLevel("Low")
                .featured(false)
                .build());

        createOptions(cozyBean, List.of(
                option("Seed", 250, 7.0, 12, "Free drip coffee on every visit, 15% off retail bean purchases, name on the Community Board"),
                option("Growth", 1500, 9.5, 18, "Bi-weekly delivery of freshly roasted beans, 20% off all caf\u00e9 purchases, private cupping session for 4"),
                option("Partner", 5000, 12.5, 24, "All Growth perks plus a farm-origin trip opportunity (travel not included), lifetime 25% caf\u00e9 discount, custom blend created in your name")
        ));

        // ──────────────────────────────────────────────────
        // 8. Verde Fresh Kitchen
        // ──────────────────────────────────────────────────
        Company verde = companyRepository.save(Company.builder()
                .name("Verde Fresh Kitchen")
                .category("Restaurant")
                .city("Miami, FL")
                .country("USA")
                .logoEmoji("\uD83E\uDD57")
                .tagline("Fresh. Fast. Feel-good food.")
                .description("Verde Fresh Kitchen emerged from founder Camila Santos's frustration with the fast-casual landscape in Miami: plenty of quick options, but almost nothing that was both genuinely healthy and delicious. A former nutritionist with a culinary degree from Johnson & Wales, Camila opened the first Verde in Miami's Wynwood neighborhood in 2020 with a simple concept: grain bowls, salads, and cold-pressed juices made with organic, locally sourced ingredients, served in under five minutes.\n\nThe formula struck a nerve. Office workers, gym-goers, and families packed the 30-seat restaurant from day one. Verde's signature \"Miami Heat Bowl\" -- quinoa, mango-habanero grilled chicken, black beans, avocado, and a cilantro-lime crema -- became a viral sensation on TikTok, generating over 2 million views and a surge in foot traffic. Today Verde serves over 300 customers daily from its single location.\n\nWith a streamlined kitchen model designed for replication, Verde is primed for multi-unit expansion. Camila has already signed letters of intent for two additional Miami locations in Brickell and Coral Gables, and investor interest has been strong from day one.")
                .founderStatement("As a nutritionist, I spent years telling people what to eat. I realized it would be far more powerful to just make that food available, affordable, and delicious. Verde isn't about restriction or diet culture -- it's about food that makes you feel genuinely good. Every bowl is designed to fuel your day, not weigh you down. This investment will help me bring Verde to two more Miami neighborhoods so that healthy eating isn't a luxury -- it's a convenience.")
                .investmentPlan("Verde is raising $350,000 to open two new locations. Each build-out costs approximately $140,000 (lease, renovation, kitchen equipment, and initial inventory). The remaining $70,000 covers pre-opening marketing campaigns, staff recruitment and training, and a centralized commissary kitchen to supply all three locations efficiently.")
                .foundedYear(2020)
                .employeeCount(11)
                .revenueLastYear(980000)
                .revenueYearBefore(760000)
                .fundingGoal(350000)
                .fundingRaised(205000)
                .riskLevel("Medium")
                .featured(true)
                .build());

        createOptions(verde, List.of(
                option("Seed", 500, 8.5, 12, "Free bowl on every third visit, 10% off all orders, early access to new menu items"),
                option("Growth", 3000, 11.5, 24, "Weekly meal plan discount (20% off 5-bowl packs), Verde branded merchandise bundle, quarterly health & nutrition workshop"),
                option("Partner", 15000, 15.0, 36, "All Growth perks plus private nutrition consultation with Camila, catering for one event per quarter (up to 20 guests), name on the Founders Board at all locations")
        ));

        // ──────────────────────────────────────────────────
        // 9. Sunset Sushi & Sake
        // ──────────────────────────────────────────────────
        Company sunsetSushi = companyRepository.save(Company.builder()
                .name("Sunset Sushi & Sake")
                .category("Restaurant")
                .city("San Diego, CA")
                .country("USA")
                .logoEmoji("\uD83C\uDF71")
                .tagline("Pacific freshness. Japanese precision.")
                .description("Sunset Sushi & Sake sits on a bluff overlooking the Pacific in San Diego's Ocean Beach neighborhood, where chef-owner Kenji Tanaka serves some of the most refined sushi on the West Coast at neighborhood prices. Kenji trained for eight years at a two-star sushi-ya in Tokyo's Ginza district before moving to California in 2015. After three years as head sushi chef at a high-end La Jolla restaurant, he opened Sunset in 2019 with a radical idea: omakase-quality fish at approachable prices.\n\nThe restaurant seats 40, including an intimate 8-seat counter where Kenji personally prepares multi-course omakase experiences. The fish is sourced daily from San Diego's Tuna Harbor Dockside Market and from a network of sustainable fisheries in Japan. The sake list, curated by Jin Tanaka (Kenji's wife and a certified sake sommelier), features 45 selections ranging from crisp junmai to rare aged koshu.\n\nSunset has built a year-round waitlist despite minimal marketing, relying entirely on word-of-mouth and a small but passionate Instagram following. The restaurant was featured in Eater San Diego's \"Essential Restaurants\" list for three consecutive years.")
                .founderStatement("In Japan, sushi is not luxury food -- it is everyday food, prepared with extraordinary care. I want to bring that philosophy to San Diego. At Sunset, you can have a transcendent piece of bluefin toro for the price of a burger. This investment will help me expand the dining room, add a dedicated sake bar, and launch our prepared sushi line for local grocery stores.")
                .investmentPlan("Sunset Sushi & Sake is raising $200,000 for a three-part growth plan: expanding the dining room by annexing the adjacent retail space to add 25 seats and a dedicated sake bar ($110,000), developing a line of prepared sushi trays and poke bowls for local grocery distribution ($50,000 for packaging, food safety certification, and initial production), and hiring an additional sushi chef and two prep cooks ($40,000 for recruiting, training, and first-quarter wages).")
                .foundedYear(2019)
                .employeeCount(10)
                .revenueLastYear(920000)
                .revenueYearBefore(790000)
                .fundingGoal(200000)
                .fundingRaised(78000)
                .riskLevel("Medium")
                .featured(false)
                .build());

        createOptions(sunsetSushi, List.of(
                option("Seed", 500, 8.0, 12, "Complimentary sake flight on every visit, 10% dining discount, priority reservations"),
                option("Growth", 2500, 10.5, 24, "Monthly omakase experience for two (valued at $120), 15% discount on all orders, invitation to exclusive sake pairing dinners"),
                option("Partner", 8000, 13.5, 36, "All Growth perks plus private omakase for 6 guests with Chef Kenji quarterly, first access to grocery sushi line, name on the Founders Wall")
        ));

        // ──────────────────────────────────────────────────
        // 10. Sweet Surrender Bakery
        // ──────────────────────────────────────────────────
        Company sweetSurrender = companyRepository.save(Company.builder()
                .name("Sweet Surrender Bakery")
                .category("Bakery")
                .city("Brooklyn, NY")
                .country("USA")
                .logoEmoji("\uD83E\uDDC1")
                .tagline("Life is short. Eat the cake.")
                .description("Sweet Surrender Bakery has been a Park Slope institution since 2016, when pastry chef Naomi Okonkwo left her position at a three-Michelin-star Manhattan restaurant to open a neighborhood bakery focused on celebration cakes, artisan breads, and French-Nigerian fusion pastries. The corner bakery with its signature pink-and-gold awning has become one of Brooklyn's most photographed storefronts.\n\nNaomi's creations blend classical French technique with the bold flavors of her Nigerian heritage. The bestselling \"Lagos Layer Cake\" -- a towering creation with coconut sponge, passion fruit curd, and suya-spiced praline -- has been featured in the New York Times and on the Today Show. The croissant menu includes a plantain-chocolate variety that sells out by 9 AM daily. Custom wedding and celebration cakes now account for 35% of revenue.\n\nSweet Surrender employs six bakers and two front-of-house staff, and recently extended hours to include a weekend brunch service featuring savory galettes and Nigerian-inspired egg dishes. The bakery's online ordering system, launched during the pandemic, now drives 20% of total sales.")
                .founderStatement("Baking is how I connect my two worlds. My French training gives me precision; my Nigerian roots give me soul. Every cake I make is a bridge between cultures. Sweet Surrender is more than a bakery -- it's a place where Park Slope families celebrate their biggest moments with something truly special. With this investment, I want to open a production kitchen so I can finally say yes to every wedding cake request and launch nationwide shipping for our signature cookies and cakes.")
                .investmentPlan("Sweet Surrender is raising $180,000 to open a dedicated production kitchen in Industry City ($100,000 for lease, build-out, and commercial baking equipment), launch a nationwide shipping program for cookies, brownies, and mini cakes ($45,000 for packaging design, cold-shipping R&D, and e-commerce platform), and hire two additional pastry chefs to handle the growing custom cake demand ($35,000 for first-year wages).")
                .foundedYear(2016)
                .employeeCount(8)
                .revenueLastYear(680000)
                .revenueYearBefore(590000)
                .fundingGoal(180000)
                .fundingRaised(72000)
                .riskLevel("Low")
                .featured(false)
                .build());

        createOptions(sweetSurrender, List.of(
                option("Seed", 250, 7.0, 12, "Free pastry on every visit, 10% off custom cake orders, birthday cupcake box delivered annually"),
                option("Growth", 1500, 9.5, 18, "Monthly surprise box of seasonal pastries, 15% discount on all orders, early access to holiday pre-orders"),
                option("Partner", 5000, 12.0, 24, "All Growth perks plus a private baking class with Naomi for 6 guests, 20% off wedding cake orders, name featured on the bakery's Founders Wall")
        ));

        // ──────────────────────────────────────────────────
        // 11. Mountain Goat Cheese Co.
        // ──────────────────────────────────────────────────
        Company mountainGoat = companyRepository.save(Company.builder()
                .name("Mountain Goat Cheese Co.")
                .category("Food Producer")
                .city("Burlington, VT")
                .country("USA")
                .logoEmoji("\uD83D\uDC10")
                .tagline("Small herd. Big flavor.")
                .description("Mountain Goat Cheese Co. began on a 30-acre hillside farm outside Burlington, Vermont, where founder and cheesemaker Elise Dumont tends a herd of 45 Alpine dairy goats. After studying cheesemaking in France's Rh\u00f4ne-Alpes region, Elise returned to Vermont in 2018 and began producing small-batch aged cheeses in a converted barn. Her ash-ripened ch\u00e8vre and cave-aged tomme quickly caught the attention of cheese shops and farm-to-table restaurants across New England.\n\nMountain Goat's cheeses have won seven awards at the American Cheese Society competition, including a first-place ribbon for the \"Green Mountain Bloom,\" a soft-ripened cheese with a delicate floral rind. The company now supplies over 50 specialty food retailers in Vermont, Massachusetts, New York, and Connecticut, and its cheeses are featured on the menus of several James Beard-nominated restaurants.\n\nProduction is currently limited by the size of the aging caves and the milking capacity of the existing barn. Demand has consistently outpaced supply, with a six-month waitlist for wholesale accounts. Elise has plans to expand the operation thoughtfully, adding capacity without compromising the artisanal methods that make Mountain Goat's cheeses exceptional.")
                .founderStatement("My goats have names, not numbers. Each one has a personality, and I swear you can taste the difference between Clover's milk and Juniper's milk in the final cheese. I'm not trying to become a factory -- I want to grow just enough to meet the demand that's been building for years. This investment will let me expand the aging caves, add a milking parlor, and bring on an apprentice cheesemaker so I can focus on developing the new aged varieties that retailers keep asking for.")
                .investmentPlan("Mountain Goat Cheese Co. is raising $85,000 for infrastructure improvements: $40,000 to excavate and outfit a second aging cave with climate-controlled shelving, $25,000 for a modern milking parlor to increase efficiency and goat comfort, and $20,000 for an apprentice cheesemaker's first-year salary plus cheesemaking equipment upgrades.")
                .foundedYear(2018)
                .employeeCount(4)
                .revenueLastYear(280000)
                .revenueYearBefore(245000)
                .fundingGoal(85000)
                .fundingRaised(38000)
                .riskLevel("Medium")
                .featured(false)
                .build());

        createOptions(mountainGoat, List.of(
                option("Seed", 250, 6.5, 12, "Quarterly cheese sampler box (3 varieties), 10% off farm shop purchases"),
                option("Growth", 1500, 9.0, 24, "Monthly artisan cheese delivery, 15% discount on all orders, guided farm visit with cheese tasting for 4"),
                option("Partner", 5000, 11.5, 36, "All Growth perks plus a private cheesemaking workshop with Elise for 6 guests, naming rights for a new cheese variety, annual farm dinner under the stars (8 guests)")
        ));

        // ──────────────────────────────────────────────────
        // 12. Neon Ramen House
        // ──────────────────────────────────────────────────
        Company neonRamen = companyRepository.save(Company.builder()
                .name("Neon Ramen House")
                .category("Restaurant")
                .city("Los Angeles, CA")
                .country("USA")
                .logoEmoji("\uD83C\uDF5C")
                .tagline("Bowls that glow. Broth with soul.")
                .description("Neon Ramen House lights up a corner of Los Angeles's Arts District with its unmistakable neon-pink signage and the rich, porky aroma of tonkotsu broth that's been simmering for 18 hours. Founder Yuki Sato opened the 35-seat ramen shop in 2020 after spending two years apprenticing at ramen shops across Fukuoka and Sapporo, followed by a stint developing recipes at a Michelin-starred izakaya in Tokyo's Shibuya district.\n\nThe menu features five signature ramens, each a carefully constructed balance of broth, noodles, and toppings. The \"Neon Tonkotsu\" -- a creamy pork bone broth with house-made wavy noodles, chashu braised for 48 hours, a jammy ajitama egg, and black garlic oil -- has been named one of LA Weekly's \"99 Essential Dishes.\" For the adventurous, the \"Electric Miso\" with Sichuan chili crisp and roasted corn has developed a cult following.\n\nNeon Ramen House has thrived in LA's competitive dining scene through a combination of exceptional food, eye-catching branding, and a late-night hours model (open until 2 AM on weekends) that captures the after-concert and nightlife crowd. Revenue has grown 25% year-over-year as the restaurant expanded delivery through its own platform to avoid third-party commission fees.")
                .founderStatement("Ramen is not fast food in Japan -- it is an art form that people dedicate their entire lives to perfecting. I spent years learning from masters who have been making the same bowl for decades. At Neon Ramen, I bring that obsessive dedication to LA, a city that deserves ramen made with zero shortcuts. This investment will fund a central noodle production facility so I can open a second location and eventually franchise the concept.")
                .investmentPlan("Neon Ramen House is raising $225,000 to build a central kitchen and noodle production facility ($120,000 for lease, build-out, noodle machines, and industrial broth kettles), open a second location in Silver Lake ($75,000 for lease deposit and initial build-out), and develop the brand's e-commerce platform for shipping frozen ramen kits nationwide ($30,000 for R&D, packaging, and marketing).")
                .foundedYear(2020)
                .employeeCount(13)
                .revenueLastYear(1050000)
                .revenueYearBefore(840000)
                .fundingGoal(225000)
                .fundingRaised(134000)
                .riskLevel("Medium")
                .featured(false)
                .build());

        createOptions(neonRamen, List.of(
                option("Seed", 250, 7.5, 12, "Free extra topping on every bowl, 10% dining discount, exclusive Neon Ramen sticker pack"),
                option("Growth", 2000, 10.0, 24, "Monthly ramen kit delivered to your door, 15% discount, VIP access to the annual Ramen Fest pop-up"),
                option("Partner", 7500, 13.0, 36, "All Growth perks plus a private ramen-making experience with Chef Yuki for 8 guests, name on the Neon Wall of Fame, early franchise investor consideration")
        ));

        // ──────────────────────────────────────────────────
        // 13. The Urban Flower Shop
        // ──────────────────────────────────────────────────
        Company urbanFlower = companyRepository.save(Company.builder()
                .name("The Urban Flower Shop")
                .category("Retail")
                .city("Atlanta, GA")
                .country("USA")
                .logoEmoji("\uD83C\uDF38")
                .tagline("Blooms that tell a story.")
                .description("The Urban Flower Shop is not your typical florist. Founded in 2019 by landscape architect turned floral designer Priya Chakraborty, the shop in Atlanta's Inman Park neighborhood takes a design-forward approach to flowers, creating arrangements that feel more like art installations than bouquets. Priya's background in landscape architecture gives her a unique eye for scale, texture, and color that sets The Urban Flower Shop apart from the cellophane-and-baby's-breath crowd.\n\nThe shop operates on a studio model: a bright, airy retail space up front where customers can browse ready-made arrangements and seasonal stems, and a design studio in the back where Priya and her team create custom pieces for weddings, corporate events, and interior design clients. Wedding florals have become the fastest-growing segment, with The Urban Flower Shop now booking 60+ weddings per year across the Southeast.\n\nPriya has also developed a thriving subscription service -- weekly and biweekly flower deliveries to homes and offices -- that now accounts for 25% of revenue and provides predictable, recurring income. The shop sources 70% of its flowers from Georgia and South Carolina farms, supporting the local cut-flower movement.")
                .founderStatement("Flowers are the oldest form of human expression. I wanted to create a shop that treats them with the same respect an architect gives a building -- thoughtful design, meaningful structure, beauty that moves you. Every arrangement I make starts with a conversation: who is it for, what do you want them to feel? This investment will help me build a larger studio, scale the subscription service, and launch a series of floral design workshops that have been waitlisted for months.")
                .investmentPlan("The Urban Flower Shop is raising $130,000 to move to a larger retail and studio space in Ponce City Market ($75,000 for lease and build-out), scale the subscription delivery service with a dedicated delivery van and cold-storage infrastructure ($35,000), and launch a quarterly floral design workshop series with an online component ($20,000 for materials, videography, and marketing).")
                .foundedYear(2019)
                .employeeCount(6)
                .revenueLastYear(480000)
                .revenueYearBefore(410000)
                .fundingGoal(130000)
                .fundingRaised(47000)
                .riskLevel("Medium")
                .featured(false)
                .build());

        createOptions(urbanFlower, List.of(
                option("Seed", 250, 7.0, 12, "10% off all purchases, free stem wrap upgrade, birthday bouquet delivered annually"),
                option("Growth", 1500, 9.5, 18, "Biweekly seasonal arrangement delivery, 15% off all orders, priority booking for wedding consultations"),
                option("Partner", 5000, 12.0, 24, "All Growth perks plus a private floral design workshop for 6 guests, 20% off wedding packages, name on the Studio Founders Board")
        ));

        // ──────────────────────────────────────────────────
        // 14. Artisan Soap & Candle Co.
        // ──────────────────────────────────────────────────
        Company artisanSoap = companyRepository.save(Company.builder()
                .name("Artisan Soap & Candle Co.")
                .category("Retail")
                .city("Asheville, NC")
                .country("USA")
                .logoEmoji("\uD83D\uDD6F\uFE0F")
                .tagline("Handcrafted. Natural. Unforgettable.")
                .description("Artisan Soap & Candle Co. started at the Asheville City Market in 2017, where founder Tess Haywood sold small batches of cold-process soaps and hand-poured soy candles from a folding table. Her products -- made entirely from natural botanicals, essential oils, and beeswax from her own backyard hives -- sold out every single market day. Within a year, she had opened a small workshop and retail space on Lexington Avenue in downtown Asheville.\n\nToday Artisan Soap & Candle Co. produces over 40 products: bar soaps, liquid soaps, soy candles, beeswax tapers, body butters, and seasonal gift sets. Every item is handcrafted in small batches using ingredients sourced from Appalachian farms and wildcrafted botanicals foraged from the Blue Ridge Mountains. The bestselling \"Mountain Morning\" candle -- a blend of Fraser fir, wild bergamot, and cedar -- has a five-star rating across 1,200 reviews on Etsy, where the shop is a Star Seller.\n\nRevenue has grown steadily through a combination of the retail storefront, farmers market sales, Etsy, and a growing wholesale business supplying boutique hotels and gift shops across the Southeast. The brand has been featured in Southern Living, Garden & Gun, and Blue Ridge Outdoors magazines.")
                .founderStatement("I believe in slow, beautiful things. Every soap I pour is a small act of resistance against the mass-produced, chemical-laden products that fill most bathroom shelves. I know every beekeeper, every lavender farmer, every wildcrafted sourced ingredient in our products by name. With this investment, I want to scale our production without losing what makes us special -- small-batch quality, real ingredients, and a deep connection to the land.")
                .investmentPlan("Artisan Soap & Candle Co. is raising $75,000 to upgrade the production workshop and scale distribution: $35,000 for larger soap molds, a commercial candle production setup, and climate-controlled curing racks, $25,000 for professional packaging redesign and a Shopify e-commerce relaunch targeting nationwide direct-to-consumer sales, and $15,000 for attending three national trade shows (NY NOW, Americasmart, Shoppe Object) to secure new wholesale accounts.")
                .foundedYear(2017)
                .employeeCount(5)
                .revenueLastYear(320000)
                .revenueYearBefore(275000)
                .fundingGoal(75000)
                .fundingRaised(31000)
                .riskLevel("Low")
                .featured(false)
                .build());

        createOptions(artisanSoap, List.of(
                option("Seed", 200, 6.5, 12, "Quarterly seasonal soap and candle box, 10% off all purchases, early access to holiday collections"),
                option("Growth", 1000, 8.5, 18, "Monthly curated product box, 20% off all orders, invitation to annual harvest gathering at the workshop"),
                option("Partner", 3500, 11.0, 24, "All Growth perks plus a private soap-making and candle-pouring workshop for 6, custom scent created for you, name on the Founders Shelf in-store")
        ));

        // ──────────────────────────────────────────────────
        // 15. The Rusty Anchor Fish & Chips
        // ──────────────────────────────────────────────────
        Company rustyAnchor = companyRepository.save(Company.builder()
                .name("The Rusty Anchor Fish & Chips")
                .category("Restaurant")
                .city("Boston, MA")
                .country("USA")
                .logoEmoji("\u2693")
                .tagline("Dockside freshness since 2019.")
                .description("The Rusty Anchor sits at the end of a weathered pier in Boston's Seaport District, serving what locals and critics alike have called the best fish and chips in New England. Founder and chef Tommy O'Sullivan grew up on a fishing boat off the coast of Galway, Ireland, and brought his family's three-generation fish frying tradition across the Atlantic when he opened The Rusty Anchor in 2019.\n\nThe restaurant is deliberately no-frills: communal picnic tables, paper-lined trays, and a menu that fits on a chalkboard. But the simplicity is deceptive. The cod is sourced daily from Gloucester day boats, the batter is Tommy's closely guarded family recipe (involving a splash of Guinness and a pinch of Old Bay), and the chips are triple-cooked in beef tallow for an impossibly crispy exterior and fluffy interior. Side offerings include house-made coleslaw, mushy peas, and a New England clam chowder that rivals anything on the waterfront.\n\nThe Rusty Anchor's unpretentious charm and exceptional quality have made it a destination. It was featured on \"Diners, Drive-Ins and Dives\" in 2023, which doubled foot traffic overnight. Revenue surged 30% last year, and Tommy is now looking to open a second location in Cambridge to serve the university crowd.")
                .founderStatement("My grandfather fried fish on the docks of Galway for fifty years. My father did the same. I'm the first O'Sullivan to do it on this side of the ocean, but the recipe hasn't changed. We don't cut corners -- fresh fish, proper batter, chips cooked the old way. When Guy Fieri came and said it was the best he'd ever had, I rang my da in Galway and he said, 'Of course it is.' This investment will help me bring The Rusty Anchor to Cambridge and eventually to every waterfront city in New England.")
                .investmentPlan("The Rusty Anchor is raising $160,000 to open a second location in Cambridge's Kendall Square ($90,000 for lease deposit, kitchen equipment, and build-out of a 40-seat space), purchase a refrigerated delivery van for catering and a potential lobster roll food truck ($40,000), and fund initial marketing and staffing for the Cambridge launch ($30,000 including hiring a head chef and line cooks).")
                .foundedYear(2019)
                .employeeCount(11)
                .revenueLastYear(890000)
                .revenueYearBefore(685000)
                .fundingGoal(160000)
                .fundingRaised(87000)
                .riskLevel("Low")
                .featured(true)
                .build());

        createOptions(rustyAnchor, List.of(
                option("Seed", 250, 7.5, 12, "Free side with every order, 10% dining discount, Rusty Anchor enamel pin"),
                option("Growth", 2000, 10.0, 24, "Monthly family fish & chips dinner for 4, 15% discount, priority seating on busy weekends"),
                option("Partner", 7500, 13.0, 36, "All Growth perks plus a private dockside dinner for 10 with Chef Tommy, name on the Crew Board at both locations, early access to franchise opportunities")
        ));

        // ──────────────────────────────────────────────────
        // Demo User Portfolio
        // ──────────────────────────────────────────────────
        seedDemoPortfolio(goldenFork, brewBrothers, emberOak, artisanSoap, verde);
    }

    private void seedDemoPortfolio(Company goldenFork, Company brewBrothers,
                                   Company emberOak, Company artisanSoap, Company verde) {
        log.info("Seeding demo user portfolio...");

        List<InvestmentOption> goldenForkOptions = investmentOptionRepository.findByCompanyId(goldenFork.getId());
        List<InvestmentOption> brewBrothersOptions = investmentOptionRepository.findByCompanyId(brewBrothers.getId());
        List<InvestmentOption> emberOakOptions = investmentOptionRepository.findByCompanyId(emberOak.getId());
        List<InvestmentOption> artisanSoapOptions = investmentOptionRepository.findByCompanyId(artisanSoap.getId());
        List<InvestmentOption> verdeOptions = investmentOptionRepository.findByCompanyId(verde.getId());

        // Investment 1: Active, invested 14 months ago -- The Golden Fork, Growth tier
        // This one shows strong paper gains (Growth tier at 11% annual return over 14 months)
        investmentRepository.save(Investment.builder()
                .userId("demo-user")
                .company(goldenFork)
                .option(goldenForkOptions.stream()
                        .filter(o -> "Growth".equals(o.getTier()))
                        .findFirst().orElseThrow())
                .amountInvested(2500.00)
                .investedAt(LocalDate.now().minusMonths(14))
                .status("Active")
                .build());

        // Investment 2: Active, invested 8 months ago -- Brew Brothers, Seed tier
        investmentRepository.save(Investment.builder()
                .userId("demo-user")
                .company(brewBrothers)
                .option(brewBrothersOptions.stream()
                        .filter(o -> "Seed".equals(o.getTier()))
                        .findFirst().orElseThrow())
                .amountInvested(500.00)
                .investedAt(LocalDate.now().minusMonths(8))
                .status("Active")
                .build());

        // Investment 3: Pending, just submitted -- Verde Fresh Kitchen, Growth tier
        investmentRepository.save(Investment.builder()
                .userId("demo-user")
                .company(verde)
                .option(verdeOptions.stream()
                        .filter(o -> "Growth".equals(o.getTier()))
                        .findFirst().orElseThrow())
                .amountInvested(3000.00)
                .investedAt(LocalDate.now())
                .status("Pending")
                .build());

        // Investment 4: Active, strong paper gains -- Ember & Oak BBQ, Partner tier
        // Invested 18 months ago at 14.5% return -- significant gains
        investmentRepository.save(Investment.builder()
                .userId("demo-user")
                .company(emberOak)
                .option(emberOakOptions.stream()
                        .filter(o -> "Partner".equals(o.getTier()))
                        .findFirst().orElseThrow())
                .amountInvested(10000.00)
                .investedAt(LocalDate.now().minusMonths(18))
                .status("Active")
                .build());

        // Investment 5: Active, less glamorous business for diversity -- Artisan Soap & Candle Co., Growth tier
        investmentRepository.save(Investment.builder()
                .userId("demo-user")
                .company(artisanSoap)
                .option(artisanSoapOptions.stream()
                        .filter(o -> "Growth".equals(o.getTier()))
                        .findFirst().orElseThrow())
                .amountInvested(1000.00)
                .investedAt(LocalDate.now().minusMonths(10))
                .status("Active")
                .build());

        log.info("Demo portfolio seeded with 5 investments.");
    }

    private record OptionData(String tier, double minInvestment, double expectedReturn,
                              int lockupMonths, String perks) {}

    private OptionData option(String tier, double minInvestment, double expectedReturn,
                              int lockupMonths, String perks) {
        return new OptionData(tier, minInvestment, expectedReturn, lockupMonths, perks);
    }

    private void createOptions(Company company, List<OptionData> optionDataList) {
        for (OptionData data : optionDataList) {
            investmentOptionRepository.save(InvestmentOption.builder()
                    .company(company)
                    .tier(data.tier())
                    .minimumInvestment(data.minInvestment())
                    .expectedAnnualReturn(data.expectedReturn())
                    .lockupMonths(data.lockupMonths())
                    .perks(data.perks())
                    .build());
        }
    }
}
