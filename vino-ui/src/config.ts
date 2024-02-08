const config = {
  API_URL: 'http://localhost:8080/api',
};

const devConfig = {
  API_URL: 'http://localhost:8080/api',
};

export default process.env.NODE_ENV === 'production' ? config : devConfig;
