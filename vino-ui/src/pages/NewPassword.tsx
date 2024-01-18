import React, { BaseSyntheticEvent, useEffect, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import loginImg from '../assets/pictures/vinoVibes-light.png';
import backgroundImg from '../assets/pictures/login-image.png';
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login } from '../redux/userSlice';
import toast from 'react-hot-toast';

const NewPassword = (): JSX.Element => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [password, setPassword] = useState<string>('');
  const [passwordRepeat, setPasswordRepeat] = useState<string>('');

  const { token } = useParams();

  const reguestNewPassword = async () => {
    if (password !== passwordRepeat) {
      toast.error('Passwörter stimmen nicht überein');
      return;
    }

    await fetch(`${config.API_URL}/forgot-password`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        password,
        passwordRepeat,
        token,
      }),
    })
      .then(async (resp) => {
        if (resp.status === 200) {
          const data = await resp.json();
          console.log(data);
          dispatch(login(data));
          navigate('/');
        } else {
          const data = await resp.json();
          console.log(data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="login">
      <img src={backgroundImg} alt="login-image" className="login-image" />
      <div className="login-container">
        <div className="logo ">
          <img src={loginImg} alt="VinoLogo" className="vino-logo" />
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
                reguestNewPassword();
              }
            }}
          />
        </div>
        <div className="group">
          <Button
            label="Neues Passwort anfordern"
            className="button"
            onClick={reguestNewPassword}
          />
        </div>
        <div className="group"></div>
      </div>
    </div>
  );
};

export default NewPassword;
