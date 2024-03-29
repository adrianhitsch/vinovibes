import React, { BaseSyntheticEvent, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import toast from 'react-hot-toast';
import { useDispatch } from 'react-redux';
import { login, setEmail } from '../redux/userSlice';
import { error } from 'console';

const Login = (): JSX.Element => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogin = async () => {
    const email = (document.getElementById('email') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;

    await fetch(`${config.API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',

      body: JSON.stringify({
        email: email || '',
        password: password || '',
      }),
    })
      .then(async (data) => {
        if (data.status === 200) {
          const response = await data.json();

          dispatch(
            login({
              token: response.token,
              email: response.email,
              firstName: response.firstName,
              lastName: response.lastName,
            }),
          );

          navigate('/');
        } else {
          const errorMesasage = await data.json();

          if (errorMesasage.status === 'PENDING') {
            dispatch(setEmail(email));
            toast.error(errorMesasage.message);
            navigate('/otp');
          } else {
            toast.error(errorMesasage.message);
          }
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };

  const handleInput = (e: any) => {
    if (e.code === 'Enter') {
      handleLogin();
    }
  };

  const handleForgotPassword = () => {
    const email = (document.getElementById('email') as HTMLInputElement).value;
    dispatch(setEmail(email));
    navigate('/forgot-password');
  };

  return (
    <>
      <div className="login">
        <img src="login-image.png" alt="login-image" className="login-image" />
        <div className="login-container">
          <div className="logo ">
            <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
            <h1>VinoVibes</h1>
          </div>
          <p>VinoVibes ist keine Website. Es ist ein Lifestyle!</p>

          <div className="group">
            <InputText type="email" name="email" id="email" placeholder="E-Mail" />
          </div>
          <div className="group">
            <InputText
              type="password"
              name="password"
              id="password"
              placeholder="Passwort"
              onKeyDown={handleInput}
            />
          </div>
          <div className="group">
            <Button
              label="Passwort vergessen?"
              onClick={handleForgotPassword}
              className="button transparent text"
            />
          </div>
          <div className="group">
            <Button label="Anmelden" onClick={handleLogin} className="button" />
          </div>
          <div className="group">
            <p>Noch keinen Account?</p>
            <Button
              label="Jetzt registrieren"
              onClick={() => navigate('/register')}
              className="button secondary"
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default Login;
