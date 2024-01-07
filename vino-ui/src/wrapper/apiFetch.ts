import React from 'react';
import { useSelector } from 'react-redux';
import config from '../config';

const apiFetch = (async (token: string, url: string, options: RequestInit = {}) => {
  const apiUrl = config.API_URL;
  
    if (token) {
      options.headers = {
        ...options.headers,
        Authorization: `Bearer ${token}`,
      };
    }

  return fetch(apiUrl + url, options);
});

export default apiFetch;
