# Requirements — Small Business Investment Marketplace

## Overview

An online marketplace (web + app) that connects growth-seeking small businesses with individual investors. Businesses receive community capital in exchange for an ownership stake — no bank loans, no single large investor required. Investors gain real, tangible equity in businesses they understand, with monthly profit-based returns.

---

## Core Value Propositions

### For Entrepreneurs
- Raise capital from the community without relying on bank loans or giving up everything to a single large investor
- Use the funds to open new locations, purchase equipment, or expand
- Gain a group of committed co-owners who are personally incentivized to promote the business

### For Investors
- Acquire ownership stakes in real, tangible businesses within industries they know
- Contribute expertise (e.g. marketing, logistics) to businesses they are invested in
- Receive monthly profit-share payments as return on investment

---

## Business / Financial Model

Companies pay a monthly percentage of their profit to investors. Two repayment structures are supported:

1. **Return-only model:** The entrepreneur pays a share of revenue until the investor has fully recouped their original investment. After that, continued dividend payments are pure profit for the investor.

2. **Return + multiplier model:** The entrepreneur pays a share of revenue until the investor recoups their original investment plus a fixed agreed-upon multiplier (e.g. 1.5×).

**Key distinction from bank loans:** Payments are always tied to profit. If the business has no profit in a given month, no payment is due.

---

## Pages & Features

### 1. Landing Page

**Goal:** Explain what the platform is and why both sides should join.

**Content requirements:**
- Clear headline communicating the platform's purpose
- Section explaining the value for entrepreneurs (see Core Value Propositions)
- Section explaining the value for investors (see Core Value Propositions)
- Explanation of how returns work (the financial model above)
- Call to action for both entrepreneurs and investors

---

### 2. Investor Side

#### 2.1 Marketplace / Browse Page
A card-based listing of available investment opportunities.

**Each card displays:**
| Field | Description |
|---|---|
| Company Name | Name of the business |
| Industry | Sector / category |
| Amount Sought | Total capital the company is raising |
| Equity Offered | Ownership percentage offered to investors |
| Risk Level | Aggregated risk indicator (low / medium / high) |

**Interactions:**
- Browse and filter by industry, risk level, amount
- Click a card to open the Investment Detail Page

#### 2.2 Investment Detail Page
A full profile for a specific investment opportunity.

**Sections:**
- **Company overview:** Description, photos, expansion plan
- **Financial metrics:** Key figures from submitted balance sheet and financials
- **Risk analysis:** Independently assessed risk report (third-party evaluation)
- **Investment terms:** Repayment model, % offered, target amount, timeline
- **Invest button / flow:** Allow a logged-in investor to commit capital

---

### 3. Entrepreneur Side

#### 3.1 Campaign Manager Dashboard
A dashboard for entrepreneurs to track their active fundraising campaign.

**Displays:**
- Total capital raised so far (amount + % of goal)
- Number of investors who have committed
- Campaign status (e.g. active, funded, closed)

#### 3.2 Data Upload / Submission
A form/interface for entrepreneurs to submit their company information for review and listing.

**Upload fields:**
- Balance sheet / financial documents
- Company photos
- Expansion / business plan document
- Other supporting materials (TBD)

---

## Secondary Market (Out of Scope — TBD)

A future feature where investors can buy and sell previously acquired ownership stakes between each other on the platform (e.g. an investor holding 10% of a brewery could list and sell that stake to another user without requiring the business to buy it back).

This is explicitly **not** in scope for the initial build and will be defined in a future requirements document.

---

## Open Questions

- [ ] What is the minimum and maximum investment amount per investor per campaign?
- [ ] Is there a platform fee? (e.g. % of capital raised, monthly SaaS fee for entrepreneurs, or spread on returns)
- [ ] Who performs the independent risk analysis — an internal team, an automated model, or an external partner?
- [ ] What is the KYC/AML requirement for investors? (regulatory compliance)
- [ ] Are there caps on how many investors can participate in a single campaign?
- [ ] What currencies and payment methods are supported?
- [ ] Is the app (mobile) in scope for V1, or web-first?
