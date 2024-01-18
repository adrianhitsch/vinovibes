import React, { useEffect } from 'react';
import './site.css';
import './icon.css';
import { Route, Routes } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import ForgotPassword from './pages/ForgotPassword';
import Otp from './pages/Otp';
import NewPassword from './pages/NewPassword';

function Router() {
  return (
    <Routes>
      <Route path="/otp" element={<Otp />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/forgot-password/" element={<ForgotPassword />} />
      <Route path="/forgot-password/:token" element={<NewPassword />} />
      <Route path="/" element={<Dashboard />} />
    </Routes>
  );
}

export default Router;
