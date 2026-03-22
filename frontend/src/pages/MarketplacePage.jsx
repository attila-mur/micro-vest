import { useTranslation } from 'react-i18next';

export default function MarketplacePage() {
  const { t } = useTranslation();

  return (
    <div className="px-4 py-6">
      <h2 className="font-display text-2xl text-text">{t('marketplace.title')}</h2>
      <div className="mt-8 rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
        <span className="text-4xl" role="img" aria-hidden="true">🏗️</span>
        <p className="mt-3 font-display text-base text-text">{t('marketplace.comingSoon')}</p>
        <p className="mt-1 text-sm text-text-muted">{t('marketplace.description')}</p>
      </div>
    </div>
  );
}
