/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#6db87b',
          dark: '#2d6a4f',
          light: '#95d5b2',
        },
        surface: {
          DEFAULT: '#ffffff',
          alt: '#f8faf8',
        },
        bg: '#f5f9f5',
        text: {
          DEFAULT: '#1b2a1b',
          muted: '#5a6b5a',
        },
        border: '#d4ddd4',
        success: '#40916c',
        warning: '#e9a820',
        danger: '#d64545',
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
      animation: {
        slideUp: 'slideUp 0.35s cubic-bezier(0.32, 0.72, 0, 1) forwards',
      },
    },
  },
  plugins: [],
};
