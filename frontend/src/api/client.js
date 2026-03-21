import axios from 'axios';

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api';

const client = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export function getCompanies() {
  return client.get('/companies').then((res) => res.data);
}

export function getCompany(id) {
  return client.get(`/companies/${id}`).then((res) => res.data);
}

export function getPlStatement(id) {
  return client.get(`/companies/${id}/pl-statement`, {
    responseType: 'blob',
  });
}

export function getPortfolio() {
  return client.get('/portfolio').then((res) => res.data);
}

export function invest(companyId, optionId, amount) {
  return client
    .post('/portfolio/invest', { companyId, optionId, amount })
    .then((res) => res.data);
}

export default client;
