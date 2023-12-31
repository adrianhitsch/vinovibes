import { Toast } from 'primereact/toast';
import React, { useEffect } from 'react';
import toast, { Toaster } from 'react-hot-toast';
import { Button } from 'primereact/button';
import { Link, useNavigate } from 'react-router-dom';

const Navigation = () => {
  const navigate = useNavigate();

  const setActive = (e: any) => {
    const elements = document.querySelectorAll('a');
    elements.forEach((el) => {
      el.classList.remove('active');
    });

    if (e.target.classList.contains('vino.logo')) {
      navigate('/');
      return;
    }

    e.target.classList.add('active');

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
  };

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
                <Link to="/vino" id="my-vino drunken">
                  Getrunkene Weine
                </Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino" id="my-vino wishlist">
                  Meine Wunschliste
                </Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/vino" id="all-vino">
              <span className="icon icon-wine-bottle"></span>
              Alle Weine
            </Link>
            <ul className="expandable menu all-vino">
              <li onClick={setActive}>
                <Link to="/vino" id="all-vino red">
                  Rotweine
                </Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino" id="all-vino white">
                  Weißweine
                </Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino" id="all-vino rose">
                  Roséweine
                </Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino" id="all-vino champaign">
                  Schaumweine
                </Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/account">
              <span className="icon icon-user"></span>Mein Account
            </Link>
          </li>
          <li onClick={setActive}>
            <Link to="/admin">
              <span className="icon icon-dev"></span>Admin
            </Link>
          </li>
        </ul>
      </nav>

      <div className="footer">
        <Button
          type="button"
          onClick={() => toast.error('Sorry could not create vino :c')}
          className="button"
        >
          <span className="icon icon-plus"></span>
          Wein erstellen
        </Button>

        <Button
          type="button"
          onClick={() => toast.error('Sorry could not log you out :c')}
          className="button transparent"
        >
          <span className="icon icon-logout"></span>
          Logout
        </Button>
      </div>
    </div>
  );
};

export default Navigation;
