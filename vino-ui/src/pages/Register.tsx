import React, { useEffect, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { Checkbox } from 'primereact/checkbox';
import toast, { Toaster } from 'react-hot-toast';
import { useDispatch } from 'react-redux';
import { login } from '../redux/userSlice';

const Register = (): JSX.Element => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [loginDisabled, setLoginDisabled] = useState<boolean>(true);
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

    const body = await fetch(`${config.API_URL}/register`, {
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
        if (resp.status === 200) {
          const data = await resp.json();
          dispatch(login({ token: data.token, email: userData.email }));
          navigate('/');
        }
      })

      .catch((err) => {
        toast.error(err);
      });
  };

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
              placeholder="E-mail"
              onInput={(e) =>
                setUserData((prevState) => ({
                  ...prevState,
                  email: e.currentTarget.value,
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
              onInput={(e) =>
                setUserData((prevState) => ({
                  ...prevState,
                  firstName: e.currentTarget.value,
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
              onInput={(e) =>
                setUserData((prevState) => ({
                  ...prevState,
                  lastName: e.currentTarget.value,
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
              onInput={(e) =>
                setUserData((prevState) => ({
                  ...prevState,
                  password: e.currentTarget.value,
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
              onInput={(e) =>
                setUserData((prevState) => ({
                  ...prevState,
                  passwordRepeat: e.currentTarget.value,
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
