/**
 * Format a number as USD currency string.
 * Examples: 840000 -> "$840,000", 12500.50 -> "$12,500.50"
 */
export function formatCurrency(value) {
  if (value == null) return '$0';
  const hasDecimals = value % 1 !== 0;
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: hasDecimals ? 2 : 0,
    maximumFractionDigits: 2,
  }).format(value);
}

/**
 * Format a number as a percentage string with 1 decimal place.
 * Example: 18.5 -> "18.5%"
 */
export function formatPercent(value) {
  if (value == null) return '0.0%';
  return `${value.toFixed(1)}%`;
}

/**
 * Format a date string as a human-readable date.
 * Example: "2024-06-15" -> "Jun 15, 2024"
 */
export function formatDate(dateStr) {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  return new Intl.DateTimeFormat('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  }).format(date);
}
