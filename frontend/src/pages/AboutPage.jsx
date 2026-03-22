import { useTranslation } from 'react-i18next';

export default function AboutPage() {
  const { t } = useTranslation();

  return (
    <div className="px-4 py-6 space-y-5">
      <div>
        <h2 className="font-display text-2xl text-text">{t('about.title')}</h2>
        <p className="mt-1 text-sm text-text-muted leading-relaxed">{t('about.subtitle')}</p>
      </div>

      <section className="rounded-2xl bg-surface p-5 shadow-sm ring-1 ring-border/50">
        <h3 className="font-display text-base text-text mb-2">{t('about.whatIs')}</h3>
        <p className="text-sm text-text-muted leading-relaxed">{t('about.whatIsDesc')}</p>
      </section>

      <section className="rounded-2xl bg-surface p-5 shadow-sm ring-1 ring-border/50">
        <div className="flex items-center gap-2 mb-3">
          <span className="text-xl" role="img" aria-hidden="true">📈</span>
          <h3 className="font-display text-base text-text">{t('about.forInvestors')}</h3>
        </div>
        <ul className="space-y-2">
          {[1, 2, 3, 4].map((i) => (
            <li key={i} className="flex items-start gap-2 text-sm text-text-muted leading-relaxed">
              <span className="mt-1 h-1.5 w-1.5 shrink-0 rounded-full bg-primary-dark" />
              {t(`about.forInvestors${i}`)}
            </li>
          ))}
        </ul>
      </section>

      <section className="rounded-2xl bg-surface p-5 shadow-sm ring-1 ring-border/50">
        <div className="flex items-center gap-2 mb-3">
          <span className="text-xl" role="img" aria-hidden="true">🏪</span>
          <h3 className="font-display text-base text-text">{t('about.forCompanies')}</h3>
        </div>
        <ul className="space-y-2">
          {[1, 2, 3, 4].map((i) => (
            <li key={i} className="flex items-start gap-2 text-sm text-text-muted leading-relaxed">
              <span className="mt-1 h-1.5 w-1.5 shrink-0 rounded-full bg-primary-dark" />
              {t(`about.forCompanies${i}`)}
            </li>
          ))}
        </ul>
      </section>

      <section className="rounded-2xl bg-gradient-to-br from-primary-dark to-emerald-900 p-5 text-white shadow-lg">
        <h3 className="font-display text-base">{t('about.howReturnsWork')}</h3>
        <p className="mt-2 text-sm text-emerald-100 leading-relaxed">{t('about.returnsDesc')}</p>

        <div className="mt-4 space-y-3">
          <div className="rounded-xl bg-white/10 p-3">
            <p className="text-xs font-semibold text-emerald-200 uppercase tracking-wider">
              {t('about.returnModel1Title')}
            </p>
            <p className="mt-1 text-sm text-emerald-100 leading-relaxed">
              {t('about.returnModel1Desc')}
            </p>
          </div>
          <div className="rounded-xl bg-white/10 p-3">
            <p className="text-xs font-semibold text-emerald-200 uppercase tracking-wider">
              {t('about.returnModel2Title')}
            </p>
            <p className="mt-1 text-sm text-emerald-100 leading-relaxed">
              {t('about.returnModel2Desc')}
            </p>
          </div>
        </div>

        <div className="mt-4 rounded-xl bg-white/15 p-3">
          <p className="text-xs font-bold text-white uppercase tracking-wider">
            {t('about.keyDifference')}
          </p>
          <p className="mt-1 text-sm text-emerald-100 leading-relaxed">
            {t('about.keyDifferenceDesc')}
          </p>
        </div>
      </section>
    </div>
  );
}
