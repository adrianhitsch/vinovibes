import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import config from '../config';
import { useNavigate } from 'react-router';
import { Checkbox } from 'primereact/checkbox';

const ForgotPassword = (): JSX.Element => {
  const navigate = useNavigate();

  const [emailSend, setEmailSend] = useState<boolean>(false);

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
              <InputText type="email" name="email" id="email" placeholder="Neues Passwort" />
            </div>
            <div className="group">
              <InputText type="email" name="email" id="email" placeholder="Passwort wiederholen" />
            </div>

            <div className="group">
              <Button label="Neues Passwort anfordern" className="button" />
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
              <InputText type="email" name="email" id="email" placeholder="E-mail" />
            </div>
            <div className="group">
              <Button
                label="Neues Passwort anfordern"
                onClick={() => setEmailSend((prevState) => !prevState)}
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
