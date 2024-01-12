import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import React, { BaseSyntheticEvent, useCallback, useEffect, useState } from 'react';
import config from '../config';
import { useNavigate } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import { registerUser } from '../redux/userSlice';
import toast, { Toaster } from 'react-hot-toast';
import { storeType } from '../redux/storeType';

const Otp = (): JSX.Element => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const email = useSelector((state: storeType) => state.user.email);

  const [otp, setOtp] = useState<Array<string>>(Array(6).fill(''));
  useEffect(() => {
    (async () => {
      if (!otp.includes('')) {
        await sendOtpTOBackend();
      }
    })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [otp]);

  const sendOtpTOBackend = useCallback(async () => {
    await fetch(`${config.API_URL}/register/otp`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        otp: otp.join(''),
      }),
    })
      .then(async (resp) => {
        if (resp.status === 200) {
          const data = await resp.json();
          dispatch(
            registerUser({
              token: data.token,
              email: data.email,
              firstName: data.firstName,
              lastName: data.lastName,
            }),
          );
          navigate('/');
        } else {
          const data = await resp.json();
          toast.error(data.message);
        }
      })

      .catch((err) => {
        toast.error(err.message);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [otp]);

  const requestNewOtp = async () => {
    setOtp(Array(6).fill(''));

    await fetch(`${config.API_URL}/register/new-otp`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email,
      }),
    })
      .then(async (resp) => {
        if (resp.status === 200) {
          toast.success('Neu E-Mail wurde versendet');
        } else {
          const data = await resp.json();
          toast.error(data.message);
        }
      })

      .catch((err) => {
        toast.error(err.message);
      });
  };

  const handleOtpChanged = async (e: BaseSyntheticEvent, index: number) => {
    let value = e.target.value;

    // check with regex if value is a number with regex
    const regex = /^[0-9\b]+$/;
    if (!regex.test(value)) {
      return;
    }

    if (value.length === 6) {
      setOtp(value.split(''));
      return;
    }

    if (value.length === 7 && otp[index] !== '') {
      value = value.slice(1);
      setOtp(value.split(''));
      return;
    }

    if (value.length > 1) {
      value = value[value.length - 1];
    }

    setOtp((prevState) => {
      const newState = [...prevState];

      newState[index] = value;
      return newState;
    });

    if (value.length === 1 && index < otp.length - 1) {
      const nextInput = document.getElementById(`otp-input-${index + 1}`);
      if (nextInput) {
        nextInput.focus();
      }
    }
  };

  const focusPrevInput = (index: number) => {
    if (index === 0) return;
    const nextInput = document.getElementById(`otp-input-${index - 1}`);
    if (nextInput) {
      nextInput.focus();
    }
  };

  const handleOtpKeyDown = (e: any, index: number) => {
    if (e.code === 'Backspace') {
      if (index > 0 && otp[index] === '') {
        setOtp((prevState) => {
          const newState = [...prevState];
          newState[index - 1] = '';
          return newState;
        });

        focusPrevInput(index);
        return;
      }
      if (index > 0) {
        setOtp((prevState) => {
          const newState = [...prevState];
          newState[index] = '';
          return newState;
        });
        // focusPrevInput(index);
        return;
      }
      setOtp((prevState) => {
        const newState = [...prevState];
        newState[index] = '';
        return newState;
      });
    }
  };

  return (
    <>
      <div className="login">
        <img src="login-image.png" alt="login-image" className="login-image" />
        <div className="otp-container">
          <div className="logo margin">
            <img src="vinoVibes-light.png" alt="VinoLogo" className="vino-logo" />
          </div>
          <h1>Verifiziere deine E-Mail</h1>
          <p>Bitte füge hier den Code ein, welchen wir per E-Mail an dich versendet haben</p>
          <div className="group center otp">
            <InputText
              type="text"
              name="otp"
              id="otp-input-0"
              value={otp[0]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 0)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 0)}
            />
            <InputText
              type="text"
              name="otp"
              id="otp-input-1"
              value={otp[1]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 1)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 1)}
            />
            <InputText
              type="text"
              name="otp"
              id="otp-input-2"
              value={otp[2]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 2)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 2)}
            />
            <InputText
              type="text"
              name="otp"
              id="otp-input-3"
              value={otp[3]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 3)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 3)}
            />
            <InputText
              type="text"
              name="otp"
              id="otp-input-4"
              value={otp[4]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 4)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 4)}
            />
            <InputText
              type="text"
              name="otp"
              id="otp-input-5"
              value={otp[5]}
              onInput={(e: BaseSyntheticEvent) => handleOtpChanged(e, 5)}
              onKeyDown={(e: any) => handleOtpKeyDown(e, 5)}
            />
          </div>
          <div className="group">
            <Button
              label="Code erneut senden"
              onClick={requestNewOtp}
              className="button transparent text"
            />
          </div>
          <div className="group"></div>
        </div>
      </div>
      <Toaster />
    </>
  );
};

export default Otp;
