import { useTranslation } from 'react-i18next';

const riskStyles = {
  Low: 'bg-success/15 text-success',
  Medium: 'bg-warning/15 text-warning',
  High: 'bg-danger/15 text-danger',
};

export default function RiskBadge({ level }) {
  const { t } = useTranslation();

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-[10px] font-semibold ${
        riskStyles[level] || riskStyles.Medium
      }`}
    >
      {t(`risk.${level}`, level)}
    </span>
  );
}
