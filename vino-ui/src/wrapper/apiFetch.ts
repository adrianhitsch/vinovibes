import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import config from '../config';
import { storeType } from '../redux/storeType';
import { logout, setToken } from '../redux/userSlice';
import { useNavigate } from 'react-router-dom';

function useApiFetch() {
  const token = useSelector((state: storeType) => state.user.token);
  const sessionEnd = useSelector((state: storeType) => state.user.sessionEnd);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const checkSession = async () => {
    if (sessionEnd < new Date().getTime()) {
      dispatch(logout());
      navigate('/login');
      return;
    }
    // 5 minutes before session end, refresh token
    if (sessionEnd < new Date().getTime() + 300000) {
      const response = await fetch(config.API_URL + '/refresh-token', {
        method: 'GET',
      });
      const data = await response.json();
      if (data.token) {
        dispatch(setToken(data.token));
      } else {
        dispatch(logout());
        navigate('/login');
      }
    }
  };

  const apiFetch = async (url: string, options: RequestInit = {}) => {
    const apiUrl = config.API_URL;

    checkSession();

    if (token) {
      options.headers = {
        ...options.headers,
        Authorization: `Bearer ${token}`,
      };
    }

    return fetch(apiUrl + url, options);
  };

  return apiFetch;
}

export default useApiFetch;
