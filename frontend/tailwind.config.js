/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#4ade80',
          dark: '#16a34a',
        },
        surface: {
          DEFAULT: '#ffffff',
          alt: '#f8fafc',
        },
        bg: '#f0fdf4',
        text: {
          DEFAULT: '#0f172a',
          muted: '#64748b',
        },
        border: '#e2e8f0',
        success: '#22c55e',
        warning: '#f59e0b',
        danger: '#ef4444',
      },
      fontFamily: {
        display: ['"DM Serif Display"', 'Georgia', 'serif'],
        sans: ['"DM Sans"', 'system-ui', 'sans-serif'],
        mono: ['"JetBrains Mono"', 'ui-monospace', 'monospace'],
      },
      borderRadius: {
        '2xl': '16px',
      },
      maxWidth: {
        app: '480px',
      },
    },
  },
  plugins: [],
};
