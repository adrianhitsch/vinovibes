import React, { BaseSyntheticEvent, useEffect, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { Checkbox } from 'primereact/checkbox';
import { useSelector } from 'react-redux';
import { storeType } from '../redux/storeType';
import { useParams } from 'react-router-dom';
import toast from 'react-hot-toast';

const ForgotPassword = (): JSX.Element => {
  const navigate = useNavigate();

  const email = useSelector((state: storeType) => state.user.email);
  console.log(email || '');
  const [inputEmail, setInputEmail] = useState<string>(email || '');

  const [emailSend, setEmailSend] = useState<boolean>(false);

  useEffect(() => {
    if (email) {
      setInputEmail(email);
    }
  }, [email]);

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
          toast.success('E-Mail wurde versendet');
        } else {
          const data = await resp.json();
          console.log(data);
          toast.error(data.message);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
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
            value={inputEmail || ''}
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
          <Button label="Login" onClick={() => navigate('/login')} className="button secondary" />
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
