import React from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';

const Login = (): JSX.Element => {
  const navigate = useNavigate();

  const login = async (e: any) => {
    const name = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;

    await fetch(`${config.API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        password: password,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
      })
      .catch((err) => {
        console.log(err);
      });

    console.log('login');
    navigate('/');
  };

  return (
    <div className="login">
      <img src="login-image.png" alt="login-image" className="login-image" />
      <div className="login-container">
        <div className="logo">
          <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
          <h1>VinoVibes</h1>
        </div>
        <p>VinoVibes ist keine Website. Es ist ein Lifestyle!</p>

        <div className="group">
          <InputText type="email" name="email" id="email" placeholder="E-mail" />
        </div>
        <div className="group">
          <InputText type="password" name="password" id="password" placeholder="Passwort" />
        </div>
        <div className="group">
          <Button label="Passwort vergessen?" className="button transparent text" />
        </div>
        <div className="group">
          <Button label="Login" onClick={login} className="button" />
        </div>
        <div className="group">
          <p>Noch keinen Account?</p>
          <Button label="Jetzt registrieren" onClick={login} className="button secondary" />
        </div>
      </div>
    </div>
  );
};

export default Login;