import axios from 'axios';

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api';

const client = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Auto-retry on network errors / 502-504 (backend still starting)
client.interceptors.response.use(null, async (error) => {
  const config = error.config;
  const status = error.response?.status;
  const isRetryable = !status || status === 502 || status === 503 || status === 504;

  if (isRetryable && (!config._retryCount || config._retryCount < 5)) {
    config._retryCount = (config._retryCount || 0) + 1;
    await new Promise((r) => setTimeout(r, config._retryCount * 2000));
    return client(config);
  }
  return Promise.reject(error);
});

export function getCompanies() {
  return client.get('/companies').then((res) => res.data);
}

export function getCompany(id) {
  return client.get(`/companies/${id}`).then((res) => res.data);
}

export function getPortfolio() {
  return client.get('/portfolio').then((res) => res.data);
}

export function invest(companyId, amount) {
  return client
    .post('/portfolio/invest', { companyId, amount })
    .then((res) => res.data);
}

export default client;
