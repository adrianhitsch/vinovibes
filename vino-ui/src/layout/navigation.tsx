import React from 'react';
import toast from 'react-hot-toast';
import { Button } from 'primereact/button';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../redux/userSlice';
import { storeType } from '../redux/storeType';
import useApiFetch from '../wrapper/apiFetch';

const Navigation = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const apiFetch = useApiFetch();

  const setActive = (e: any) => {
    const elements = document.querySelectorAll('li');
    elements.forEach((el) => {
      el.classList.remove('active');
    });

    if (e.target.classList.contains('vino.logo')) {
      navigate('/');
      return;
    }

    e.target.parentNode.classList.add('active');

    if (e.target.id.includes('my-vino')) {
      const submenus = document.querySelector('.expandable.menu.my-vino');
      submenus?.classList.add('expand');
    } else {
      const submenus = document.querySelector('.expandable.menu.my-vino');
      submenus?.classList.remove('expand');
    }

    if (e.target.id.includes('all-vino')) {
      const submenus = document.querySelector('.expandable.menu.all-vino');
      submenus?.classList.add('expand');
    } else {
      const submenus = document.querySelector('.expandable.menu.all-vino');
      submenus?.classList.remove('expand');
    }

    if (e.target.id.includes('account')) {
      const submenus = document.querySelector('.expandable.menu.account');
      submenus?.classList.add('expand');
    } else {
      const submenus = document.querySelector('.expandable.menu.account');
      submenus?.classList.remove('expand');
    }
  };

  const handleLogout = async () => {
    dispatch(logout());

    // TODO - logout from backend
  };

  const handleHello = async () => {
    const test = await apiFetch('/hello', { method: 'GET' }).then((data) => data.text());
    console.log(test);
  };

  // mobile navigation
  if (window.innerWidth <= 575) {
    return null;
  }

  return (
    <div className="main-nav">
      <div className="logo-container">
        <img src="vinoVibes.png" alt="VinoLogo" className="vino-logo" onClick={setActive} />
      </div>
      <nav>
        <ul>
          <li onClick={setActive}>
            <Link to="/">
              {' '}
              <span className="icon icon-dashboard"></span>Dashboard
            </Link>
          </li>
          <li onClick={setActive}>
            <Link to="/vino" id="my-vino">
              <span className="icon icon-wine-glass"></span>
              Meine Weine
            </Link>
            <ul className="expandable menu my-vino">
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/vino" id="my-vino drunken">
                  Getrunkene Weine
                </Link>
              </li>
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/vino" id="my-vino wishlist">
                  Meine Wunschliste
                </Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/all-vines" id="all-vino">
              <span className="icon icon-wine-bottle"></span>
              Alle Weine
            </Link>
            <ul className="expandable menu all-vino">
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/all-vines" id="all-vino red">
                  Rotweine
                </Link>
              </li>
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/all-vines" id="all-vino white">
                  Weißweine
                </Link>
              </li>
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/vino" id="all-vino rose">
                  Roséweine
                </Link>
              </li>
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/vino" id="all-vino champaign">
                  Schaumweine
                </Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/account" id="account">
              <span className="icon icon-user"></span>Mein Account
            </Link>

            <ul className="expandable menu account">
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/account" id="account">
                  Mein Profil
                </Link>
              </li>
              <li onClick={setActive}>
                <span className="icon icon-circle"></span>
                <Link to="/account" id="account">
                  Einstellungen
                </Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/admin">
              <span className="icon icon-dev"></span>Admin
            </Link>
          </li>
        </ul>
      </nav>

      <div className="footer">
        <Button type="button" onClick={() => navigate('/create-vino')} className="button">
          <span className="icon icon-plus"></span>
          Wein erstellen
        </Button>

        {/* ! TODO - add logout functionality  */}
        <Button type="button" onClick={handleLogout} className="button transparent">
          <span className="icon icon-logout"></span>
          Logout
        </Button>
      </div>
    </div>
  );
};

export default Navigation;
