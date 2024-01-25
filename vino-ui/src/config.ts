const config = {
  API_URL: process.env.API_URL,
};

const devConfig = {
  API_URL: 'http://localhost:8080/api',
};

export default process.env.NODE_ENV === 'production' ? config : devConfig;
