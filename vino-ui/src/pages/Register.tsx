import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { Checkbox } from 'primereact/checkbox';

const Register = (): JSX.Element => {
  const navigate = useNavigate();

  const [checked, setChecked] = useState<{ cb1: boolean; cb2: boolean }>({
    cb1: false,
    cb2: false,
  });

  const register = async (e: any) => {
    const email = (document.getElementById('email') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;

    const body = await fetch(`${config.API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: email || '',
        password: password || '',
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
        <div className="logo margin">
          <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
        </div>

        <div className="group">
          <InputText type="email" name="email" id="email" placeholder="E-mail" />
        </div>
        <div className="group">
          <InputText type="text" name="firstname" id="firstname" placeholder="Vorname" />
        </div>
        <div className="group">
          <InputText type="text" name="lastname" id="lastname" placeholder="Nachname" />
        </div>
        <div className="group">
          <InputText type="password" name="password" id="password" placeholder="Passwort" />
        </div>
        <div className="group">
          <InputText
            type="password"
            name="password"
            id="password-repeat"
            placeholder="Passwort wiederholen"
          />
        </div>
        <div className="group">
          <Checkbox
            inputId="cb1"
            name="cb1"
            value="age"
            checked={checked.cb1}
            onClick={() => setChecked((prevState) => ({ ...prevState, cb1: !prevState.cb1 }))}
          />
          <label htmlFor="cb1" className="p-checkbox-label">
            Ich bin mindestens 18 Jahre alt
          </label>
        </div>
        <div className="group">
          <Checkbox
            inputId="cb2"
            name="cb2"
            value="agb"
            checked={checked.cb2}
            onClick={() => setChecked((prevState) => ({ ...prevState, cb2: !prevState.cb2 }))}
          />
          <label htmlFor="cb2" className="p-checkbox-label">
            Ich habe die Datenschutzbestimmungen gelesen und akzeptiere diese.
          </label>
        </div>

        <div className="group">
          <Button label="Registrieren" onClick={register} className="button register" />
        </div>
        <div className="group">
          <p>Account bereits vorhanden ?</p>
          <Button label="Login" onClick={() => navigate('/login')} className="button secondary" />
        </div>
      </div>
    </div>
  );
};

export default Register;
