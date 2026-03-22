import { useEffect, useState, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getCompany, invest } from '../api/client';
import { formatCurrency } from '../utils/format';
import RiskBadge from '../components/RiskBadge';

export default function CompanyPage() {
  const { id } = useParams();
  const { t } = useTranslation();
  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showInvest, setShowInvest] = useState(false);

  const fetchCompany = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getCompany(id);
      setCompany(data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchCompany();
  }, [fetchCompany]);

  if (loading) return <Skeleton />;
  if (error) {
    return (
      <div className="px-4 py-8 text-center">
        <p className="text-sm text-text-muted">{t('common.error')}</p>
        <button onClick={fetchCompany} className="mt-3 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white">
          {t('common.retry')}
        </button>
      </div>
    );
  }

  return (
    <div className="px-4 py-5">
      <Link to="/browse" className="text-xs text-primary-dark font-semibold">
        ← {t('company.back')}
      </Link>

      <div className="mt-4 flex items-start gap-3">
        <span className="text-4xl" role="img" aria-hidden="true">{company.logoEmoji}</span>
        <div>
          <h2 className="font-display text-xl text-text">{company.name}</h2>
          <p className="text-xs text-text-muted">{company.category} · {company.city}</p>
          <p className="mt-1 text-sm text-text-muted italic">{company.tagline}</p>
        </div>
      </div>

      <div className="mt-5 grid grid-cols-2 gap-3">
        <MetricCard label={t('company.revenue')} value={formatCurrency(company.revenueLastYear)} />
        <MetricCard label={t('company.employees')} value={company.employeeCount} />
        <MetricCard label={t('company.founded')} value={company.foundedYear} />
        <MetricCard label={t('company.risk')} value={<RiskBadge level={company.riskLevel} />} />
        <MetricCard label={t('company.equityOffered')} value={`${company.equityOffered}%`} highlight />
        <MetricCard label={t('company.amountSought')} value={formatCurrency(company.amountSought)} />
        <MetricCard label={t('company.profitShare')} value={`${company.profitSharePercent}%`} className="col-span-2" highlight />
      </div>

      {company.description && (
        <Section title={t('company.about')}>
          <p className="text-sm text-text-muted leading-relaxed">{company.description}</p>
        </Section>
      )}

      {company.founderStatement && (
        <Section title={t('company.founderSays')}>
          <blockquote className="border-l-3 border-primary pl-3 text-sm text-text-muted italic leading-relaxed">
            "{company.founderStatement}"
          </blockquote>
        </Section>
      )}

      {company.investmentPlan && (
        <Section title={t('company.investmentPlan')}>
          <p className="text-sm text-text-muted leading-relaxed">{company.investmentPlan}</p>
        </Section>
      )}

      <button
        onClick={() => setShowInvest(true)}
        className="mt-6 w-full rounded-xl bg-primary-dark py-3 text-sm font-semibold text-white shadow-md transition-transform hover:-translate-y-0.5"
      >
        {t('company.invest')}
      </button>

      {showInvest && (
        <InvestModal
          company={company}
          onClose={() => setShowInvest(false)}
        />
      )}
    </div>
  );
}

function MetricCard({ label, value, highlight, className = '' }) {
  return (
    <div className={`rounded-xl bg-surface p-3 shadow-sm ring-1 ring-border/50 ${className}`}>
      <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">{label}</p>
      <p className={`mt-0.5 font-mono text-sm font-semibold ${highlight ? 'text-primary-dark' : 'text-text'}`}>
        {value}
      </p>
    </div>
  );
}

function Section({ title, children }) {
  return (
    <section className="mt-5">
      <h3 className="font-display text-sm text-text mb-2">{title}</h3>
      {children}
    </section>
  );
}

function InvestModal({ company, onClose }) {
  const { t } = useTranslation();
  const [amount, setAmount] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(null);

  const handleSubmit = async () => {
    const num = parseFloat(amount);
    if (!num || num <= 0) return;
    setSubmitting(true);
    try {
      const result = await invest(company.id, num);
      setSuccess(result);
    } catch {
      // ignore for demo
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/40" onClick={onClose}>
      <div
        className="w-full max-w-app rounded-t-2xl bg-surface p-5 shadow-xl animate-slideUp"
        onClick={(e) => e.stopPropagation()}
      >
        {success ? (
          <div className="text-center py-4">
            <span className="text-4xl">🎉</span>
            <p className="mt-2 font-display text-base text-text">{t('company.investSuccess')}</p>
            <p className="mt-1 text-sm text-primary-dark font-semibold">
              {t('company.equityAcquired', { percent: success.equityShareAcquired })}
            </p>
            <button onClick={onClose} className="mt-4 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white">
              OK
            </button>
          </div>
        ) : (
          <>
            <h3 className="font-display text-base text-text">
              {t('company.investIn', { name: company.name })}
            </h3>
            <div className="mt-4">
              <label className="text-xs text-text-muted font-medium" htmlFor="invest-amount">
                {t('company.amount')} (USD)
              </label>
              <input
                id="invest-amount"
                type="number"
                min="1"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="1000"
                className="mt-1 w-full rounded-lg border border-border bg-white px-3 py-2 font-mono text-sm text-text focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>
            <div className="mt-4 flex gap-3">
              <button
                onClick={onClose}
                className="flex-1 rounded-lg border border-border px-4 py-2 text-sm font-semibold text-text-muted"
              >
                {t('company.cancel')}
              </button>
              <button
                onClick={handleSubmit}
                disabled={submitting || !amount}
                className="flex-1 rounded-lg bg-primary-dark px-4 py-2 text-sm font-semibold text-white disabled:opacity-50"
              >
                {submitting ? '...' : t('company.confirmInvestment')}
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

function Skeleton() {
  return (
    <div className="px-4 py-5 space-y-4">
      <div className="skeleton-shimmer h-4 w-16 rounded" />
      <div className="flex gap-3">
        <div className="skeleton-shimmer h-12 w-12 rounded-xl" />
        <div className="flex-1 space-y-2">
          <div className="skeleton-shimmer h-5 w-3/4 rounded" />
          <div className="skeleton-shimmer h-3 w-1/2 rounded" />
        </div>
      </div>
      <div className="grid grid-cols-2 gap-3">
        {[...Array(6)].map((_, i) => (
          <div key={i} className="skeleton-shimmer h-16 rounded-xl" />
        ))}
      </div>
    </div>
  );
}
