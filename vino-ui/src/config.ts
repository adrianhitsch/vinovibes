const config = {
  API_URL: process.env.API_URL,
};

const devConfig = {
  API_URL: 'http://backend.vinovibes.local',
};

export default process.env.NODE_ENV === 'production' ? config : devConfig;
