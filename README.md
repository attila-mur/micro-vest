# 🌱 MicroVest

> **Invest in the places you love.**

MicroVest is a mobile-first investment platform that lets everyday people invest in small local
businesses — restaurants, craft breweries, cafés, bakeries, and other community-rooted companies.
Think of it as a stock exchange for Main Street, not Wall Street.

---

## Features

- 🏠 **Landing Page** — explains the platform with "how it works" steps
- 📋 **Company Browser** — scrollable card feed with category filters, equity offered, and risk rating
- 🔍 **Company Detail** — full page with metrics, founder quote, investment plan, and invest button
- 💼 **Portfolio View** — see your equity stakes and total invested
- 🎯 **Investment Flow** — invest and acquire equity (demo mode, no real payments)
- 🌐 **Bilingual** — Hungarian (default) and English, switchable from the header
- 📖 **About Page** — value propositions for investors and entrepreneurs
- 🏗️ **Marketplace** — placeholder for future secondary market

---

## Tech Stack

| Layer      | Technology                                         |
|------------|----------------------------------------------------|
| Backend    | Java 21, Spring Boot 3.x, Maven                    |
| Database   | H2 (in-memory, resets on restart)                  |
| ORM        | Spring Data JPA / Hibernate                        |
| Frontend   | React 18, Vite, TailwindCSS, React Router, react-i18next |
| i18n       | Hungarian (default) + English                      |
| API        | REST/JSON, prefixed `/api/**`                      |
| Container  | Docker (single container, nginx + Java + supervisor)|
| Hosting    | Render (single Docker Web Service)                 |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         User's Browser                          │
│                   (Mobile-first, 390px wide)                    │
└─────────────────────────┬───────────────────────────────────────┘
                          │ HTTP / REST JSON
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Frontend (React + Vite)                     │
│  ┌──────────┐ ┌──────────┐ ┌───────────┐ ┌───────────────┐  │
│  │ Landing  │ │  Browse  │ │  Company  │ │   Portfolio   │  │
│  │   (/)    │ │ (/browse)│ │(/company) │ │ (/portfolio)  │  │
│  └──────────┘ └──────────┘ └───────────┘ └───────────────┘  │
│         React Router · react-i18next (HU/EN)                 │
│              nginx proxies /api/* → backend                   │
└─────────────────────────┬───────────────────────────────────────┘
                          │ /api/**
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                  Backend (Spring Boot :8080)                    │
│  ┌───────────────────────┐  ┌──────────────────────────────┐   │
│  │   CompanyController   │  │    PortfolioController       │   │
│  │  GET /companies       │  │  GET  /portfolio             │   │
│  │  GET /companies/{id}  │  │  POST /portfolio/invest      │   │
│  │  GET /companies/{id}  │  └──────────────────────────────┘   │
│  │       /pl-statement   │                                      │
│  └───────────────────────┘                                      │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │               Service Layer (business logic)              │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │        Spring Data JPA Repositories                       │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │             H2 In-Memory Database                         │  │
│  │   Companies · InvestmentOptions · Investments            │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Data Model

### Company
| Field | Type | Description |
|---|---|---|
| id | Long | Primary key |
| name | String | Business name |
| category | String | Restaurant / Brewery / Café / Bakery / Retail |
| city | String | Location (e.g. "Austin, TX") |
| logoEmoji | String | Placeholder logo emoji |
| tagline | String | One-line pitch |
| description | Text | Short company description |
| founderStatement | Text | First-person quote from founder |
| investmentPlan | Text | How funds will be used |
| foundedYear | int | Year the company was founded |
| employeeCount | int | Current headcount |
| revenueLastYear | double | Last year revenue in USD |
| equityOffered | double | % of ownership available to investors |
| amountSought | double | Total capital being raised in USD |
| profitSharePercent | double | % of monthly profits distributed to investors |
| riskLevel | String | Low / Medium / High |
| featured | boolean | Show at top of browse feed |

### Investment (Portfolio)
| Field | Type | Description |
|---|---|---|
| userId | String | Always `"demo-user"` in this demo |
| company | Company | FK |
| amountInvested | double | Amount committed |
| equityShareAcquired | double | % of equity acquired |
| investedAt | LocalDate | Date of investment |
| status | String | Active / Pending / Exited |

---

## API Reference

### Companies

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies` | List all companies (summary cards) |
| GET | `/api/companies/{id}` | Full company detail |

**Example response — GET /api/companies**
```json
[
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
]
```

### Portfolio

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/portfolio` | Demo user's portfolio summary |
| POST | `/api/portfolio/invest` | Submit an investment (demo, always succeeds) |

**Example response — GET /api/portfolio**
```json
{
  "totalInvested": 21500.00,
  "investments": [
    {
      "id": 1,
      "companyName": "The Golden Fork",
      "companyId": 1,
      "logoEmoji": "🍽️",
      "category": "Restaurant",
      "amountInvested": 2500.00,
      "equityShareAcquired": 0.25,
      "investedAt": "2024-01-22",
      "status": "Active"
    }
  ]
}
```

---

## Seeded Companies

The app launches with 15 pre-loaded companies:

| # | Company | Category | City |
|---|---------|----------|------|
| 1 | The Golden Fork | Restaurant | Austin, TX |
| 2 | Brew Brothers Craft Brewery | Brewery | Portland, OR |
| 3 | Morning Bloom Café | Café | Nashville, TN |
| 4 | Ember & Oak BBQ | Restaurant | Kansas City, MO |
| 5 | La Bella Trattoria | Restaurant | Chicago, IL |
| 6 | Hop Yard Brewing Co. | Brewery | Denver, CO |
| 7 | The Cozy Bean Roasters | Café | Seattle, WA |
| 8 | Verde Fresh Kitchen | Restaurant | Miami, FL |
| 9 | Sunset Sushi & Sake | Restaurant | San Diego, CA |
| 10 | Sweet Surrender Bakery | Bakery | Brooklyn, NY |
| 11 | Mountain Goat Cheese Co. | Food Producer | Burlington, VT |
| 12 | Neon Ramen House | Restaurant | Los Angeles, CA |
| 13 | The Urban Flower Shop | Retail | Atlanta, GA |
| 14 | Artisan Soap & Candle Co. | Retail | Asheville, NC |
| 15 | The Rusty Anchor Fish & Chips | Restaurant | Boston, MA |

The demo user (`demo-user`) has pre-seeded investments in 5 of these companies.

---

## Running Locally

### Prerequisites
- Docker & docker-compose
- Java 21 (for running backend without Docker)
- Node.js 20+ (for running frontend without Docker)

### Full stack with Docker

```bash
git clone <repo-url>
cd microvest
docker-compose up --build
```

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| H2 Console | http://localhost:8080/h2-console |

### Backend only

```bash
cd backend
mvn spring-boot:run
# API available at http://localhost:8080/api
```

### Frontend only

```bash
cd frontend
npm install
npm run dev
# App at http://localhost:5173 (proxies /api to localhost:8080)
```

---

## Deployment on Render

The app runs as a **single Docker web service** — nginx serves the frontend and proxies `/api` to the Java backend, both inside one container.

### Setup
1. Create a new **Web Service** on Render
2. **Environment:** Docker
3. **Root Directory:** (leave blank — uses repo root `Dockerfile`)
4. Render auto-detects the port (the container reads the `PORT` env var)

That's it. No env vars needed for the basic deploy.

---

## Design System

| Token | Value | Usage |
|-------|-------|-------|
| `--color-primary` | `#4ade80` | CTAs, active states, badges |
| `--color-primary-dark` | `#16a34a` | Hover states, text on light |
| `--color-bg` | `#f0fdf4` | App background |
| `--color-surface` | `#ffffff` | Cards |
| `--color-text` | `#0f172a` | Body text |
| `--color-text-muted` | `#64748b` | Secondary text |

**Fonts:** DM Serif Display (headings) · DM Sans (body) · JetBrains Mono (financial figures)

---

## Project Status

| Area | Status |
|------|--------|
| Backend API (profit-share model) | ✅ |
| Data seeding (15 companies) | ✅ |
| Landing page | ✅ |
| About / Mission page | ✅ |
| Browse page (with filters) | ✅ |
| Company detail page | ✅ |
| Portfolio page | ✅ |
| Investment flow (demo) | ✅ |
| i18n (Hungarian + English) | ✅ |
| Docker + Render deployment | ✅ |
| Secondary marketplace | 🏗️ Placeholder |
| Entrepreneur dashboard | ❌ Future |
| Real auth | ❌ Future |
| Real payments | ❌ Future |
| Persistent DB | ❌ Future |

---

## Contributing & Maintenance

This project is maintained with Claude Code. See `CLAUDE.md` for full architectural guidelines,
code conventions, and instructions for Claude when making changes.

**When making changes:** always keep this README in sync with the actual implementation.
If you add endpoints, change the data model, or alter the deployment setup — update the
relevant section here.

---

*MicroVest — small investments, big impact.*
