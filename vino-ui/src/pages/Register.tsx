import React, { BaseSyntheticEvent, useEffect, useState, useCallback } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { Checkbox } from 'primereact/checkbox';
import toast, { Toaster } from 'react-hot-toast';
import { useDispatch } from 'react-redux';
import { registerUser } from '../redux/userSlice';
import Otp from './Otp';

const Register = (): JSX.Element => {
  const navigate = useNavigate();

  const [loginDisabled, setLoginDisabled] = useState<boolean>(true);
  const [validateEmail, setValidateEmail] = useState<boolean>(false);
  const [userData, setUserData] = useState<{
    email: string;
    password: string;
    passwordRepeat: string;
    firstName: string;
    lastName: string;
  }>({
    email: '',
    password: '',
    passwordRepeat: '',
    firstName: '',
    lastName: '',
  });

  const [checked, setChecked] = useState<{ cb1: boolean; cb2: boolean }>({
    cb1: false,
    cb2: false,
  });

  useEffect(() => {
    if (validateEmail) {
      const input = document.getElementById('otp-input-0');
      if (input) {
        input.focus();
      }
    }
  }, [validateEmail]);

  useEffect(() => {
    if (
      userData.email &&
      userData.password &&
      userData.passwordRepeat &&
      userData.firstName &&
      userData.lastName &&
      checked.cb1 &&
      checked.cb2
    ) {
      setLoginDisabled(false);
    } else {
      setLoginDisabled(true);
    }
  }, [userData, checked]);

  const register = async (e: any) => {
    console.log(userData);
    if (userData.password !== userData.passwordRepeat) {
      toast.error('Passwörter stimmen nicht überein');
      return;
    }

    await fetch(`${config.API_URL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: userData.email || '',
        password: userData.passwordRepeat || '',
        passwordRepeat: userData.passwordRepeat || '',
        firstName: userData.firstName || '',
        lastName: userData.lastName || '',
        eighteen: checked.cb1,
        privacy: checked.cb2,
      }),
    })
      .then(async (resp) => {
        if (resp.status === 201) {
          setValidateEmail(true);
        } else {
          const data = await resp.json();
          toast.error(data.message);
        }
      })

      .catch((err) => {
        toast.error(err.message);
      });
  };

  if (validateEmail) {
    return <Otp email={userData.email || ''} />;
  }

  return (
    <>
      <div className="login">
        <img src="login-image.png" alt="login-image" className="login-image" />
        <div className="login-container">
          <div className="logo margin">
            <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
          </div>

          <div className="group">
            <InputText
              type="email"
              name="email"
              id="email"
              placeholder="E-Mail"
              onInput={(e: BaseSyntheticEvent) =>
                setUserData((prevState) => ({
                  ...prevState,
                  email: e.target.value,
                }))
              }
            />
          </div>
          <div className="group">
            <InputText
              type="text"
              name="firstname"
              id="firstname"
              placeholder="Vorname"
              onInput={(e: BaseSyntheticEvent) =>
                setUserData((prevState) => ({
                  ...prevState,
                  firstName: e.target.value,
                }))
              }
            />
          </div>
          <div className="group">
            <InputText
              type="text"
              name="lastname"
              id="lastname"
              placeholder="Nachname"
              onInput={(e: BaseSyntheticEvent) =>
                setUserData((prevState) => ({
                  ...prevState,
                  lastName: e.target.value,
                }))
              }
            />
          </div>
          <div className="group">
            <InputText
              type="password"
              name="password"
              id="password"
              placeholder="Passwort"
              onInput={(e: BaseSyntheticEvent) =>
                setUserData((prevState) => ({
                  ...prevState,
                  password: e.target.value,
                }))
              }
            />
          </div>
          <div className="group">
            <InputText
              type="password"
              name="password"
              id="password-repeat"
              placeholder="Passwort wiederholen"
              onInput={(e: BaseSyntheticEvent) =>
                setUserData((prevState) => ({
                  ...prevState,
                  passwordRepeat: e.target.value,
                }))
              }
            />
          </div>
          <div className="group">
            <Checkbox
              inputId="cb1"
              name="cb1"
              value="age"
              checked={checked.cb1}
              className="p-invalid"
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
            <Button
              label="Registrieren"
              onClick={register}
              className="button register"
              disabled={loginDisabled}
            />
          </div>
          <div className="group">
            <p>Account bereits vorhanden ?</p>
            <Button label="Login" onClick={() => navigate('/login')} className="button secondary" />
          </div>
        </div>
      </div>
      <Toaster />
    </>
  );
};

export default Register;
