import React, { BaseSyntheticEvent, useEffect, useRef, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { useSelector } from 'react-redux';
import { storeType } from '../redux/storeType';
import { useLocation } from 'react-router-dom';
import toast from 'react-hot-toast';

const ForgotPassword = (): JSX.Element => {
  const navigate = useNavigate();
  const location = useLocation();

  const email = useSelector((state: storeType) => state.user.email);
  const [inputEmail, setInputEmail] = useState<string>(email || '');
  const [password, setPassword] = useState<string>('');
  const [passwordRepeat, setPasswordRepeat] = useState<string>('');
  const token = useRef<string>('');

  const [emailSend, setEmailSend] = useState<boolean>(false);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    token.current = searchParams.get('token') || '';
    console.log(token.current);

    if (token.current) {
      setEmailSend(true);
    }
  }, [location]);

  useEffect(() => {
    if (email) {
      setInputEmail(email);
    }
  }, [email]);

  const sendNewPassword = async () => {
    if (password !== passwordRepeat) {
      toast.error('Passwörter stimmen nicht überein');
      return;
    }

    await fetch(`${config.API_URL}/reset-password`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        token: token.current,
        password,
        passwordRepeat,
      }),
    })
      .then(async (resp) => {
        if (resp.status === 200) {
          toast.success('Passwort erfolgreich geändert');
          navigate('/login');
        } else {
          const data = await resp.json();
          toast.error(data.message);
        }
      })
      .catch((err) => {
        toast.error('Passwort konnte nicht geändert werden');
      });
  };

  const reguestNewPassword = async () => {
    await fetch(`${config.API_URL}/forgot-password`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: inputEmail,
      }),
    })
      .then(async (resp) => {
        if (resp.status === 200) {
          toast.success('E-Mail erfolgreich versendet');
        } else {
          const data = await resp.json();
          toast.error(data.message);
        }
      })
      .catch(() => {
        toast.error('E-Mail konnte nicht versendet werden');
      });
  };

  return (
    <>
      {emailSend ? (
        <div className="login">
          <img src="login-image.png" alt="login-image" className="login-image" />
          <div className="login-container">
            <div className="logo ">
              <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
              <h1>VinoVibes</h1>
            </div>
            <p>VinoVibes ist keine Website. Es ist ein Lifestyle!</p>
            <div className="group">
              <InputText
                type="password"
                placeholder="Neues Passwort"
                onInput={(e: BaseSyntheticEvent) => setPassword(e.target.value)}
              />
            </div>
            <div className="group">
              <InputText
                type="password"
                placeholder="Passwort wiederholen"
                onInput={(e: BaseSyntheticEvent) => setPasswordRepeat(e.target.value)}
                onKeyDown={(e: any) => {
                  if (e.key === 'Enter') {
                    sendNewPassword();
                  }
                }}
              />
            </div>
            <div className="group">
              <Button
                label="Neues Passwort anfordern"
                className="button"
                onClick={sendNewPassword}
              />
            </div>
            <div className="group"></div>
          </div>
        </div>
      ) : (
        <div className="login">
          <img src="login-image.png" alt="login-image" className="login-image" />
          <div className="login-container">
            <div className="logo ">
              <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
              <h1>VinoVibes</h1>
            </div>
            <p>VinoVibes ist keine Website. Es ist ein Lifestyle!</p>
            <div className="group">
              <InputText
                type="email"
                name="email"
                id="email"
                placeholder="E-Mail"
                value={inputEmail}
                onInput={(e: BaseSyntheticEvent) => setInputEmail(e.target.value)}
                onKeyDown={(e: any) => {
                  if (e.key === 'Enter') {
                    reguestNewPassword();
                  }
                }}
              />
            </div>
            <div className="group">
              <Button
                label="Neues Passwort anfordern"
                onClick={() => reguestNewPassword()}
                className="button"
              />
            </div>
            <div className="group">
              <p>Passwort wieder eingefallen?</p>
              <Button
                label="Login"
                onClick={() => navigate('/login')}
                className="button secondary"
              />
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ForgotPassword;
