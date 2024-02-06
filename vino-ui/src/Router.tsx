import React, { useEffect } from 'react';
import './site.css';
import './icon.css';
import { Route, Routes } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import ForgotPassword from './pages/ForgotPassword';
import Otp from './pages/Otp';
import TableView from './pages/TableView';
import DetailPage from './pages/DetailPage';
import CreateVino from './pages/createVino';

function Router() {
  return (
    <Routes>
      <Route path="/otp" element={<Otp />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route path="/" element={<Dashboard />} />
      <Route path="/all-vines" element={<TableView />} />
      <Route path="/vine-detail" element={<DetailPage />} />
      <Route path="create-vino" element={<CreateVino />} />
    </Routes>
  );
}

export default Router;
