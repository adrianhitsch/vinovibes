import React from 'react';
import { useSelector } from 'react-redux';

const ApiFetch = (async (url: string, options: RequestInit = {}) => {
  const token = useSelector((state: any) => state.user.token);
 
    if (!token) {
      options.headers = {
        ...options.headers,
        Authorization: `Bearer ${token}`,
      };
    }
    const response = await fetch(url, options);

  return response;
});

export default ApiFetch;
