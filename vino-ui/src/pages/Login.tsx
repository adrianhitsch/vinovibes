import React, { BaseSyntheticEvent } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import toast, { Toaster } from 'react-hot-toast';

const Login = (): JSX.Element => {
  const navigate = useNavigate();

  const handleLogin = async () => {
    const email = (document.getElementById('email') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;

    console.log(email, password);

    const body = await fetch(`${config.API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        name: email || '',
        password: password || '',
      }),
    })
      // .then((res) => res.json())
      .then((data) => {
        if (data.status === 200) {
          console.log(data);

          navigate('/');
        } else {
          toast.error('E-Mail oder Passwort ist falsch. Bitte versuche es erneut.');
        }
      })
      .catch((err) => {
        console.log(err);
        toast.error(err.message);
      });
  };

  const handleInput = (e: any) => {
    console.log(e);
    if (e.code === 'Enter') {
      handleLogin();
    }
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
            <InputText type="email" name="email" id="email" placeholder="E-mail" />
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
              onClick={() => navigate('/forgot-password')}
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
      <Toaster />
    </>
  );
};

export default Login;
