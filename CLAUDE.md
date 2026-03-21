# CLAUDE.md — Project Instructions for MicroVest

> This file guides Claude Code in building and maintaining the MicroVest application.
> **Keep this file and README.md in sync.** If you make architectural decisions, add new modules,
> change the API contract, or alter the tech stack, update README.md accordingly.

---

## Project Overview

**MicroVest** is a mobile-first investment platform for everyday people to invest in small local
businesses — restaurants, craft breweries, cafés, bakeries, and similar community companies.
Think of it as a stock exchange, but for Main Street instead of Wall Street.

Users can:
- Browse local companies with key financial metrics on a card view
- Open a company profile to read its story, investment plan, and download a P&L statement
- View their own investment portfolio (demo user pre-loaded with investments)

---

## Naming & Branding

- **App name:** MicroVest
- **Tagline:** *Invest in the places you love.*
- **Brand color:** Light green — primary `#4ade80`, dark variant `#16a34a`, background tint `#f0fdf4`
- **Visual tone:** Clean, trustworthy, approachable. Not a cold fintech. Warm but professional.

---

## Tech Stack

| Layer      | Technology                                      |
|------------|-------------------------------------------------|
| Backend    | Java 21, Spring Boot 3.x, Maven                 |
| Database   | H2 in-memory (no persistence needed for demo)  |
| ORM        | Spring Data JPA / Hibernate                     |
| Frontend   | React 18 + Vite, TailwindCSS                    |
| API        | REST JSON, served at `/api/**`                  |
| Container  | Docker (multi-stage builds), docker-compose     |
| Hosting    | Render (see deployment section)                 |

---

## Repository Structure

```
microvest/
├── CLAUDE.md                   ← you are here
├── README.md                   ← keep updated
├── docker-compose.yml          ← local dev orchestration
│
├── backend/                    ← Spring Boot project
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/microvest/
│           │   ├── MicrovestApplication.java
│           │   ├── config/
│           │   │   ├── CorsConfig.java
│           │   │   └── DataSeeder.java       ← seeds all 15 companies + demo portfolio
│           │   ├── controller/
│           │   │   ├── CompanyController.java
│           │   │   └── PortfolioController.java
│           │   ├── model/
│           │   │   ├── Company.java
│           │   │   ├── Investment.java
│           │   │   └── InvestmentOption.java
│           │   ├── repository/
│           │   │   ├── CompanyRepository.java
│           │   │   └── InvestmentRepository.java
│           │   └── service/
│           │       ├── CompanyService.java
│           │       └── PortfolioService.java
│           └── resources/
│               ├── application.properties
│               └── pl-statements/            ← mock PDF files (one per company)
│
└── frontend/                   ← React + Vite project
    ├── Dockerfile
    ├── nginx.conf
    ├── package.json
    ├── vite.config.js
    ├── tailwind.config.js
    └── src/
        ├── main.jsx
        ├── App.jsx
        ├── api/
        │   └── client.js                     ← Axios base config
        ├── components/
        │   ├── CompanyCard.jsx
        │   ├── CompanyDrawer.jsx              ← slide-up detail sheet
        │   ├── InvestmentOptions.jsx
        │   ├── PortfolioCard.jsx
        │   ├── BottomNav.jsx
        │   └── Header.jsx
        ├── pages/
        │   ├── BrowsePage.jsx
        │   └── PortfolioPage.jsx
        └── styles/
            └── index.css
```

---

## Data Model

### Company

```java
@Entity
public class Company {
    Long id;
    String name;
    String category;           // "Restaurant", "Brewery", "Café", "Bakery", etc.
    String city;
    String country;
    String logoEmoji;          // use emojis as placeholder logos (e.g. "🍺")
    String tagline;            // one-line pitch
    String description;        // 2–3 paragraph story, shown in detail view
    String founderStatement;   // quote from the founder
    String investmentPlan;     // how they will use the investment money
    int foundedYear;
    int employeeCount;
    double revenueLastYear;    // in USD
    double revenueYearBefore;  // for growth % calculation
    double fundingGoal;        // total round size in USD
    double fundingRaised;      // already committed
    String riskLevel;          // "Low", "Medium", "High"
    boolean featured;
}
```

### InvestmentOption

```java
@Entity
public class InvestmentOption {
    Long id;
    Company company;
    String tier;               // e.g. "Seed", "Growth", "Partner"
    double minimumInvestment;
    double expectedAnnualReturn; // percentage
    int lockupMonths;
    String perks;              // e.g. "10% discount at the restaurant"
}
```

### Investment (portfolio entry for demo user)

```java
@Entity
public class Investment {
    Long id;
    String userId;             // always "demo-user" for now
    Company company;
    InvestmentOption option;
    double amountInvested;
    LocalDate investedAt;
    String status;             // "Active", "Pending", "Exited"
}
```

---

## Seed Data — 15 Companies

Seed all companies in `DataSeeder.java` using `@PostConstruct` or `ApplicationRunner`.
Create realistic, rich data. Here are the 15 companies to seed:

| # | Name | Category | City | Emoji |
|---|------|----------|------|-------|
| 1 | The Golden Fork | Restaurant | Austin, TX | 🍽️ |
| 2 | Brew Brothers Craft Brewery | Brewery | Portland, OR | 🍺 |
| 3 | Morning Bloom Café | Café | Nashville, TN | ☕ |
| 4 | Ember & Oak BBQ | Restaurant | Kansas City, MO | 🔥 |
| 5 | La Bella Trattoria | Restaurant | Chicago, IL | 🍝 |
| 6 | Hop Yard Brewing Co. | Brewery | Denver, CO | 🌿 |
| 7 | The Cozy Bean Roasters | Café | Seattle, WA | ☕ |
| 8 | Verde Fresh Kitchen | Restaurant | Miami, FL | 🥗 |
| 9 | Sunset Sushi & Sake | Restaurant | San Diego, CA | 🍱 |
| 10 | Sweet Surrender Bakery | Bakery | Brooklyn, NY | 🧁 |
| 11 | Mountain Goat Cheese Co. | Food Producer | Burlington, VT | 🐐 |
| 12 | Neon Ramen House | Restaurant | Los Angeles, CA | 🍜 |
| 13 | The Urban Flower Shop | Retail | Atlanta, GA | 🌸 |
| 14 | Artisan Soap & Candle Co. | Retail | Asheville, NC | 🕯️ |
| 15 | The Rusty Anchor Fish & Chips | Restaurant | Boston, MA | ⚓ |

For each company, write:
- A compelling `description` (2–3 paragraphs, narrative, specific)
- A personal `founderStatement` (first-person, warm, specific)
- A concrete `investmentPlan` (specific use of funds, e.g. "open second location", "new brewing equipment")
- Realistic revenue figures (range: $180k–$2.4M), with 5–30% YoY growth
- Funding goals between $50k–$500k, partially filled
- 2–3 `InvestmentOption` tiers per company

### Demo User Portfolio

Pre-invest the demo user (`userId = "demo-user"`) in **5 of the 15 companies** with varied amounts,
dates, and statuses so the portfolio page is interesting:

- 2 Active investments (invested 6–18 months ago)
- 1 Pending investment (just submitted)
- 1 that shows strong paper gains
- 1 in a less glamorous business to show diversity

---

## REST API Contract

All endpoints prefixed with `/api`.

### Companies

```
GET  /api/companies                    → List<CompanySummaryDTO>
GET  /api/companies/{id}              → CompanyDetailDTO
GET  /api/companies/{id}/pl-statement → PDF file download
```

**CompanySummaryDTO** (for browse cards):
```json
{
  "id": 1,
  "name": "The Golden Fork",
  "category": "Restaurant",
  "city": "Austin, TX",
  "logoEmoji": "🍽️",
  "tagline": "Farm-to-table done right.",
  "foundedYear": 2018,
  "employeeCount": 12,
  "revenueLastYear": 840000,
  "revenueGrowthPercent": 18.5,
  "fundingGoal": 150000,
  "fundingRaised": 67000,
  "riskLevel": "Medium",
  "featured": true
}
```

**CompanyDetailDTO** — all of the above plus:
```json
{
  "description": "...",
  "founderStatement": "...",
  "investmentPlan": "...",
  "investmentOptions": [ ... ]
}
```

### Portfolio

```
GET  /api/portfolio                    → PortfolioDTO (for demo-user)
POST /api/portfolio/invest             → InvestmentDTO (demo action, always succeeds)
```

**PortfolioDTO:**
```json
{
  "totalInvested": 12500.00,
  "estimatedValue": 14230.00,
  "investments": [ ... ]
}
```

For the P&L statement, generate a simple fake PDF on the fly using iText or Apache PDFBox,
or serve a pre-generated static PDF. Keep it simple — a one-page summary is fine.

---

## Frontend Design Directives

This is critical. The frontend must be **genuinely beautiful**, not generic.

### Must-haves
- **Mobile-first:** Design for 390px width. Desktop should be a centered card (max-width 480px).
- **Bottom navigation bar** with two tabs: Browse (🔍) and Portfolio (💼)
- **Company cards** in a scrollable feed — not a table. Each card shows: emoji logo, name,
  category badge, city, revenue, growth %, funding progress bar, risk badge.
- **Company detail** opens as a **bottom drawer / sheet** (slides up from bottom, 90vh),
  NOT a new page. Shows full description, founder statement, investment plan, and investment
  option cards. Has a download P&L button.
- **Portfolio page** shows total invested, estimated value (with gain %), and a list of
  investment cards grouped by status.

### Typography
Do **not** use Inter or Roboto. Choose something with character:
- Display/headings: `DM Serif Display` or `Playfair Display` — gives warmth and trust
- Body: `DM Sans` or `Nunito` — readable, friendly
- Numbers/stats: Monospaced for financial figures (e.g. `JetBrains Mono` or `IBM Plex Mono`)

Load from Google Fonts.

### Color Palette
```css
--color-primary:     #4ade80;   /* bright green */
--color-primary-dark:#16a34a;   /* dark green */
--color-bg:          #f0fdf4;   /* very light green tint */
--color-surface:     #ffffff;
--color-surface-alt: #f8fafc;
--color-text:        #0f172a;
--color-text-muted:  #64748b;
--color-border:      #e2e8f0;
--color-success:     #22c55e;
--color-warning:     #f59e0b;
--color-danger:      #ef4444;
```

### Component Behaviors
- Funding progress bar: animated on load, green fill, shows `$X raised of $Y goal`
- Risk badge: green=Low, amber=Medium, red=High
- Revenue growth: show `▲ 18.5%` in green or `▼ 3.2%` in red
- Cards have a subtle shadow, rounded corners (16px), hover lift effect
- Investing action: a simple modal/bottom sheet confirming the amount — no real payment flow
- Loading skeleton cards while fetching

---

## Backend Implementation Notes

- Use `@CrossOrigin` or a global `CorsConfig` bean to allow frontend dev server (`localhost:5173`)
- Use DTOs — never expose JPA entities directly from controllers
- Use `@Value("${app.demo-user-id:demo-user}")` pattern for demo user ID
- P&L PDF endpoint: generate with Apache PDFBox or serve a static resource. If generating,
  include company name, year, mock revenue/expense table, and a disclaimer watermark.
- Validation: use `@Valid` and Bean Validation on any POST endpoints
- Use `application.properties` for H2 console (enabled in dev, disabled in prod profile)

### application.properties
```properties
spring.application.name=microvest
spring.datasource.url=jdbc:h2:mem:microvest
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.h2.console.enabled=true
server.port=8080
```

---

## Docker & Deployment

### Backend Dockerfile (multi-stage)
```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Frontend Dockerfile (multi-stage with nginx)
```dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
```

### nginx.conf
Proxy `/api/**` requests to the backend container. Serve the React SPA with `try_files`
so client-side routing works.

```nginx
server {
    listen 80;
    root /usr/share/nginx/html;
    index index.html;

    location /api/ {
        proxy_pass http://backend:8080/api/;
        proxy_set_header Host $host;
    }

    location / {
        try_files $uri $uri/ /index.html;
    }
}
```

### docker-compose.yml
```yaml
version: '3.9'
services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    depends_on:
      - backend
```

### Render Deployment
- Deploy backend as a **Web Service** (Docker), port 8080
- Deploy frontend as a **Static Site** (Vite build output `dist/`) OR as a Docker Web Service
- Set env var `VITE_API_BASE_URL` to the backend Render URL for production builds
- In `client.js`: `const baseURL = import.meta.env.VITE_API_BASE_URL || '/api'`

---

## Development Workflow

### Running locally (full stack)
```bash
docker-compose up --build
# Frontend: http://localhost:3000
# Backend:  http://localhost:8080
# H2 console: http://localhost:8080/h2-console
```

### Running individually
```bash
# Backend
cd backend && mvn spring-boot:run

# Frontend
cd frontend && npm install && npm run dev
# Vite proxies /api → localhost:8080 (configure in vite.config.js)
```

### Vite proxy config (vite.config.js)
```js
export default {
  server: {
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
}
```

---

## Code Quality Rules

- **No TODOs left in committed code.** If something is out of scope, document it in README.
- **DTOs for all API responses** — no raw entity serialization.
- **No `System.out.println`** — use SLF4J `@Slf4j` logger.
- **Frontend:** components should be small and focused. If a component exceeds ~120 lines, split it.
- **CSS:** use Tailwind utility classes. Avoid inline styles except for dynamic values.
- **Accessibility:** all interactive elements must have `aria-label` or visible labels.
- **Error states:** every API call in the frontend must handle loading, error, and empty states.

---

## README Update Policy

> **Whenever you:**
> - Add a new endpoint
> - Change the data model
> - Add a new dependency
> - Change Docker or deployment configuration
> - Add a new page or significant feature
>
> **→ Update README.md to reflect the change.**
>
> The README is the source of truth for any developer picking up this project.
> Keep the architecture diagram and API table in the README accurate at all times.

---

## Current Progress

### What's Done
- **All backend files created** (21 files) — models, DTOs, repositories, services, controllers, config, DataSeeder with all 15 companies, PDF generation with PDFBox
- **All frontend files created** (22 files) — React components, pages, API client, Tailwind config, styles with animations, Google Fonts setup
- **docker-compose.yml** created at project root
- **Frontend `npm install`** completed successfully (node_modules present)

### What's Left
1. **Install `openjdk-21-jdk`** — only the JRE is installed, `javac` is missing. Run: `sudo apt install openjdk-21-jdk`
2. **Start backend** — `cd backend && mvn spring-boot:run` (first run will download Maven deps ~1-2 min)
3. **Start frontend** — `cd frontend && npm run dev` (Vite dev server on port 5173, proxies /api to localhost:8080)
4. **Fix any compilation/runtime errors** that surface on first boot
5. **Visual QA** — verify the UI matches design specs

### Known State
- Maven dependencies were already downloaded to `~/.m2/repository` during the failed build attempt, so next build should be faster
- Node.js 18.19.1 is installed (not 20+, but should work fine with Vite 6 and React 18)

---

## Out of Scope (for this demo)

- Real authentication (use hardcoded `demo-user`)
- Real payment processing
- Persistent database (H2 in-memory is sufficient)
- Email notifications
- Admin dashboard
- Real PDF financial statements (mock data is fine)
- Multi-currency support
