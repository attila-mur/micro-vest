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
- Open a company profile to read its story and investment plan
- Invest in companies and receive equity + profit-share
- View their own investment portfolio (demo user pre-loaded with investments)
- Switch between Hungarian (default) and English UI

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
| Frontend   | React 18 + Vite, TailwindCSS, React Router, react-i18next |
| i18n       | Hungarian (default) + English                   |
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
│           │   │   └── Investment.java
│           │   ├── repository/
│           │   │   ├── CompanyRepository.java
│           │   │   └── InvestmentRepository.java
│           │   └── service/
│           │       ├── CompanyService.java
│           │       └── PortfolioService.java
│           └── resources/
│               └── application.properties
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
        ├── i18n/
        │   ├── i18n.js                      ← i18n config (HU default)
        │   ├── hu.json                      ← Hungarian translations
        │   └── en.json                      ← English translations
        ├── components/
        │   ├── CompanyCard.jsx
        │   ├── PortfolioCard.jsx
        │   ├── RiskBadge.jsx
        │   └── Header.jsx                   ← nav + language switcher
        ├── pages/
        │   ├── LandingPage.jsx              ← / (hero + how it works)
        │   ├── AboutPage.jsx                ← /about (mission, value props)
        │   ├── BrowsePage.jsx               ← /browse (card feed + filters)
        │   ├── CompanyPage.jsx              ← /company/:id (detail + invest)
        │   ├── PortfolioPage.jsx            ← /portfolio (user investments)
        │   └── MarketplacePage.jsx          ← /marketplace (coming soon)
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
    String description;        // short company description
    String founderStatement;   // quote from the founder
    String investmentPlan;     // how they will use the investment money
    int foundedYear;
    int employeeCount;
    double revenueLastYear;    // in USD
    double equityOffered;      // % of ownership available to investors
    double amountSought;       // total capital being raised in USD
    double profitSharePercent; // % of monthly profits distributed to investors
    String riskLevel;          // "Low", "Medium", "High"
    boolean featured;
}
```

### Investment (portfolio entry for demo user)

```java
@Entity
public class Investment {
    Long id;
    String userId;             // always "demo-user" for now
    Company company;
    double amountInvested;
    double equityShareAcquired; // % of equity acquired
    LocalDate investedAt;
    String status;             // "Active", "Pending", "Exited"
}
```

### Business Model

Investors acquire ownership stakes in running businesses and receive monthly profit-share payments.
Payments are tied to actual profits — if no profit in a given month, no payment is due.

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
- Realistic revenue figures (range: $180k–$2.4M)
- Equity offered: 5–25%, amount sought: $50k–$500k, profit share: 15–25%

### Demo User Portfolio

Pre-invest the demo user (`userId = "demo-user"`) in **5 of the 15 companies** with varied amounts,
dates, and statuses so the portfolio page is interesting.

---

## REST API Contract

All endpoints prefixed with `/api`.

### Companies

```
GET  /api/companies                    → List<CompanySummaryDTO>
GET  /api/companies/{id}              → CompanyDetailDTO
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
  "equityOffered": 15.0,
  "amountSought": 150000,
  "profitSharePercent": 20.0,
  "riskLevel": "Medium",
  "featured": true
}
```

**CompanyDetailDTO** — all of the above plus:
```json
{
  "description": "...",
  "founderStatement": "...",
  "investmentPlan": "..."
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
  "totalInvested": 21500.00,
  "investments": [ ... ]
}
```

**InvestRequest (POST body):**
```json
{
  "companyId": 1,
  "amount": 5000
}
```

---

## Frontend Design Directives

This is critical. The frontend must be **genuinely beautiful**, not generic.

### Must-haves
- **Mobile-first:** Design for 390px width. Desktop should be a centered card (max-width 480px).
- **Top navigation** in header with links: Browse, Portfolio, About, Marketplace + language switcher (HU/EN)
- **React Router** for page navigation (/, /browse, /company/:id, /portfolio, /about, /marketplace)
- **i18n:** All UI strings via react-i18next. Hungarian default, English available.
- **Company cards** in a scrollable feed — not a table. Each card shows: emoji logo, name,
  category badge, city, equity offered, amount sought, profit share %, risk badge.
- **Company detail** is a full page (/company/:id). Shows description, founder statement,
  investment plan, key metrics, and invest button.
- **Portfolio page** shows total invested and a list of investment cards grouped by status.
- **Landing page** (/) with hero section and "how it works" steps.
- **About page** with value propositions for investors and entrepreneurs.

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
- Risk badge: green=Low, amber=Medium, red=High (translated via i18n)
- Cards have a subtle shadow, rounded corners (16px), hover lift effect
- Investing action: a simple modal/bottom sheet confirming the amount — no real payment flow
- Loading skeleton cards while fetching
- Category filter pills on browse page

---

## Backend Implementation Notes

- Use `@CrossOrigin` or a global `CorsConfig` bean to allow frontend dev server (`localhost:5173`)
- Use DTOs — never expose JPA entities directly from controllers
- Use `@Value("${app.demo-user-id:demo-user}")` pattern for demo user ID
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
- **Backend:** Models (Company, Investment), DTOs, services, controllers, DataSeeder with 15 companies, demo portfolio
- **Frontend:** React Router, react-i18next (HU/EN), 6 pages (Landing, About, Browse, Company, Portfolio, Marketplace)
- **Deployment:** Single Docker container (nginx + Java via supervisor), deployed on Render
- **Both `mvn compile` and `npm run build` pass cleanly**

### What's NOT in scope (demo app)
- Real authentication (hardcoded demo-user)
- Real payments
- Persistent database (H2 in-memory)
- PDF financial statements
- Entrepreneur dashboard (future)
- Secondary marketplace (future)

---

## Out of Scope (for this demo)

- Real authentication (use hardcoded `demo-user`)
- Real payment processing
- Persistent database (H2 in-memory is sufficient)
- Email notifications
- Admin dashboard
- Real PDF financial statements (mock data is fine)
- Multi-currency support
