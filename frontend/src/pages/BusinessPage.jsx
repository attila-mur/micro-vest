import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { formatCurrency } from '../utils/format';

export default function BusinessPage() {
  const { t } = useTranslation();
  const [view, setView] = useState('dashboard'); // 'dashboard' or 'add'

  return (
    <div className="px-4 py-6">
      <h2 className="font-display text-2xl text-text">{t('business.title')}</h2>
      <p className="mt-1 text-sm text-text-muted">{t('business.subtitle')}</p>

      <div className="mt-4 flex gap-2">
        <button
          onClick={() => setView('dashboard')}
          className={`rounded-full px-3 py-1 text-xs font-semibold transition-colors ${
            view === 'dashboard'
              ? 'bg-primary-dark text-white'
              : 'bg-surface text-text-muted ring-1 ring-border/50'
          }`}
        >
          {t('business.yourCompany')}
        </button>
        <button
          onClick={() => setView('add')}
          className={`rounded-full px-3 py-1 text-xs font-semibold transition-colors ${
            view === 'add'
              ? 'bg-primary-dark text-white'
              : 'bg-surface text-text-muted ring-1 ring-border/50'
          }`}
        >
          {t('business.addCompany')}
        </button>
      </div>

      {view === 'dashboard' ? <DemoDashboard /> : <AddCompanyForm />}
    </div>
  );
}

function DemoDashboard() {
  const { t } = useTranslation();

  // Demo data for "The Golden Fork" entrepreneur view
  const company = {
    name: 'The Golden Fork',
    emoji: '🍽️',
    category: 'Restaurant',
    city: 'Austin, TX',
    status: 'Active',
    amountSought: 150000,
    capitalRaised: 67500,
    investorCount: 12,
  };

  const progress = Math.round((company.capitalRaised / company.amountSought) * 100);

  const documents = [
    { key: 'uploadBalance', uploaded: true },
    { key: 'uploadPhotos', uploaded: true },
    { key: 'uploadPlan', uploaded: false },
    { key: 'uploadOther', uploaded: false },
  ];

  return (
    <div className="mt-5 space-y-4">
      <div className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
        <div className="flex items-center gap-3">
          <span className="text-3xl">{company.emoji}</span>
          <div>
            <h3 className="font-display text-base text-text">{company.name}</h3>
            <p className="text-xs text-text-muted">{company.category} · {company.city}</p>
          </div>
          <span className="ml-auto rounded-full bg-success/15 px-2 py-0.5 text-[10px] font-semibold text-success">
            {t('business.statusActive')}
          </span>
        </div>
      </div>

      <div className="rounded-2xl bg-gradient-to-br from-primary-dark to-emerald-900 p-5 text-white shadow-lg">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <p className="text-[10px] uppercase tracking-wider text-emerald-200 font-medium">
              {t('business.capitalRaised')}
            </p>
            <p className="mt-1 font-mono text-2xl font-bold">
              {formatCurrency(company.capitalRaised)}
            </p>
            <p className="text-xs text-emerald-200">
              {progress}% {t('business.ofGoal')}
            </p>
          </div>
          <div>
            <p className="text-[10px] uppercase tracking-wider text-emerald-200 font-medium">
              {t('business.investorCount')}
            </p>
            <p className="mt-1 font-mono text-2xl font-bold">
              {company.investorCount}
            </p>
          </div>
        </div>

        <div className="mt-4 h-2 rounded-full bg-white/20 overflow-hidden">
          <div
            className="h-full rounded-full bg-white/80 transition-all duration-1000"
            style={{ width: `${progress}%` }}
          />
        </div>
        <p className="mt-1 text-xs text-emerald-200 text-right">
          {formatCurrency(company.capitalRaised)} / {formatCurrency(company.amountSought)}
        </p>
      </div>

      <section className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
        <h3 className="font-display text-sm text-text mb-3">{t('business.documents')}</h3>
        <div className="space-y-2">
          {documents.map((doc) => (
            <div key={doc.key} className="flex items-center justify-between rounded-xl bg-bg p-3">
              <span className="text-xs text-text-muted">{t(`business.${doc.key}`)}</span>
              {doc.uploaded ? (
                <span className="rounded-full bg-success/15 px-2 py-0.5 text-[10px] font-semibold text-success">
                  {t('business.uploaded')}
                </span>
              ) : (
                <button className="rounded-lg bg-primary-dark px-3 py-1 text-[10px] font-semibold text-white">
                  {t('business.uploadBalance').split(' ')[0]}
                </button>
              )}
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}

function AddCompanyForm() {
  const { t } = useTranslation();

  return (
    <div className="mt-5 space-y-4">
      <div className="rounded-2xl bg-surface p-5 shadow-sm ring-1 ring-border/50">
        <p className="text-sm text-text-muted mb-4">{t('business.addCompanyDesc')}</p>
        <div className="space-y-3">
          <FormField label={t('business.companyName')} placeholder="The Golden Fork" />
          <FormField label={t('business.category')} placeholder="Restaurant" />
          <FormField label={t('business.city')} placeholder="Austin, TX" />
          <div>
            <label className="text-xs text-text-muted font-medium">{t('business.description')}</label>
            <textarea
              rows={3}
              placeholder="..."
              className="mt-1 w-full rounded-lg border border-border bg-white px-3 py-2 text-sm text-text focus:outline-none focus:ring-2 focus:ring-primary resize-none"
            />
          </div>
          <button className="w-full rounded-xl bg-primary-dark py-3 text-sm font-semibold text-white shadow-md transition-transform hover:-translate-y-0.5">
            {t('business.submit')}
          </button>
        </div>
      </div>
    </div>
  );
}

function FormField({ label, placeholder }) {
  return (
    <div>
      <label className="text-xs text-text-muted font-medium">{label}</label>
      <input
        type="text"
        placeholder={placeholder}
        className="mt-1 w-full rounded-lg border border-border bg-white px-3 py-2 text-sm text-text focus:outline-none focus:ring-2 focus:ring-primary"
      />
    </div>
  );
}
