import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

const featuredCompanies = [
  { emoji: '🍽️', name: 'The Golden Fork', category: 'Restaurant', city: 'Austin, TX', equity: '15%', id: 1 },
  { emoji: '🍺', name: 'Brew Brothers', category: 'Brewery', city: 'Portland, OR', equity: '20%', id: 2 },
  { emoji: '☕', name: 'Morning Bloom Café', category: 'Café', city: 'Nashville, TN', equity: '12%', id: 3 },
];

export default function LandingPage() {
  const { t } = useTranslation();

  return (
    <div className="px-4 py-6">
      {/* Hero */}
      <section className="relative overflow-hidden rounded-2xl bg-gradient-to-br from-primary-dark to-emerald-900 px-5 py-10 text-center text-white shadow-lg">
        <div className="absolute inset-0 opacity-10">
          <div className="absolute -left-8 -top-8 h-40 w-40 rounded-full bg-white" />
          <div className="absolute -bottom-6 -right-6 h-32 w-32 rounded-full bg-white" />
        </div>
        <div className="relative">
          <span className="text-5xl" role="img" aria-hidden="true">🌱</span>
          <h2 className="mt-4 font-display text-3xl leading-snug">
            {t('landing.title')}
          </h2>
          <p className="mx-auto mt-3 max-w-sm text-sm leading-relaxed text-emerald-100">
            {t('landing.subtitle')}
          </p>
          <div className="mt-6 flex flex-col gap-3 sm:flex-row sm:justify-center">
            <Link
              to="/browse"
              className="rounded-xl bg-white px-6 py-3 text-sm font-semibold text-primary-dark shadow-md transition-transform hover:-translate-y-0.5"
            >
              {t('landing.ctaInvestor')}
            </Link>
            <Link
              to="/business"
              className="rounded-xl border-2 border-white/60 px-6 py-3 text-sm font-semibold text-white transition-transform hover:-translate-y-0.5 hover:bg-white/10"
            >
              {t('landing.ctaCompany')}
            </Link>
          </div>
        </div>
      </section>

      {/* Stats */}
      <section className="mt-6 grid grid-cols-3 gap-3">
        {[1, 2, 3].map((i) => (
          <div key={i} className="rounded-xl bg-surface p-3 text-center shadow-sm ring-1 ring-border/50">
            <p className="font-mono text-lg font-bold text-primary-dark">
              {t(`landing.stat${i}Value`)}
            </p>
            <p className="mt-0.5 text-[10px] text-text-muted leading-tight">
              {t(`landing.stat${i}Label`)}
            </p>
          </div>
        ))}
      </section>

      {/* How it works */}
      <section className="mt-8">
        <h3 className="font-display text-lg text-text text-center">
          {t('landing.howItWorks')}
        </h3>
        <div className="mt-5 space-y-4">
          <Step number="1" title={t('landing.step1Title')} desc={t('landing.step1Desc')} />
          <Step number="2" title={t('landing.step2Title')} desc={t('landing.step2Desc')} />
          <Step number="3" title={t('landing.step3Title')} desc={t('landing.step3Desc')} />
        </div>
      </section>

      {/* Featured companies */}
      <section className="mt-8">
        <h3 className="font-display text-lg text-text text-center">
          {t('landing.featured')}
        </h3>
        <div className="mt-4 space-y-3">
          {featuredCompanies.map((c) => (
            <Link
              key={c.id}
              to={`/company/${c.id}`}
              className="flex items-center gap-3 rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50 transition-transform hover:-translate-y-0.5"
            >
              <span className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-primary/15 text-2xl">
                {c.emoji}
              </span>
              <div className="flex-1 min-w-0">
                <p className="font-display text-sm text-text truncate">{c.name}</p>
                <p className="text-[11px] text-text-muted">{c.category} · {c.city}</p>
              </div>
              <div className="text-right shrink-0">
                <p className="font-mono text-sm font-bold text-primary-dark">{c.equity}</p>
                <p className="text-[10px] text-text-muted">{t('browse.equityOffered')}</p>
              </div>
            </Link>
          ))}
        </div>
        <div className="mt-4 text-center">
          <Link
            to="/browse"
            className="inline-block rounded-xl bg-surface px-5 py-2.5 text-sm font-semibold text-primary-dark shadow-sm ring-1 ring-border/50 transition-transform hover:-translate-y-0.5"
          >
            {t('landing.featuredCta')} →
          </Link>
        </div>
      </section>
    </div>
  );
}

function Step({ number, title, desc }) {
  return (
    <div className="flex items-start gap-4 rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
      <span className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-primary/20 font-mono text-sm font-bold text-primary-dark">
        {number}
      </span>
      <div>
        <h4 className="font-display text-sm font-semibold text-text">{title}</h4>
        <p className="mt-0.5 text-xs text-text-muted leading-relaxed">{desc}</p>
      </div>
    </div>
  );
}
