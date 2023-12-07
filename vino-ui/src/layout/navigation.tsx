import { Toast } from "primereact/toast";
import React, { useEffect } from "react";
import toast, { Toaster } from "react-hot-toast";
import { Button } from "primereact/button";
import { Link, useNavigate } from "react-router-dom";

const Navigation = () => {
  const navigate = useNavigate();

  const setActive = (e: any) => {
    const elements = document.querySelectorAll("a");
    elements.forEach((el) => {
      el.classList.remove("active");
    });

    if (e.target.classList.contains("vino.logo")) {
      navigate("/");
      return;
    }

    e.target.classList.add("active");
  };

  return (
    <div className="main-nav">
      <div className="logo-container">
        <img
          src="vinoVibes.png"
          alt="VinoLogo"
          className="vino-logo"
          onClick={setActive}
        />
      </div>
      <nav>
        <ul>
          <li onClick={setActive}>
            <Link to="/">Dashboard</Link>
          </li>
          <li>
            <li onClick={setActive}>
              {/* <img src="svg/wine-glass-solid.svg" alt="Wein" /> */}
              <Link to="/vino">Meine Weine</Link>
            </li>
            <ul>
              <li onClick={setActive}>
                <Link to="/vino">Getrunkene Weine</Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino">Meine Wunschliste</Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/vino">Alle Weine</Link>
          </li>
          <li>
            <ul>
              <li onClick={setActive}>
                <Link to="/vino">Rotweine</Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino">Weißweine</Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino">Roséweine</Link>
              </li>
              <li onClick={setActive}>
                <Link to="/vino">Schaumweine</Link>
              </li>
            </ul>
          </li>
          <li onClick={setActive}>
            <Link to="/account">Mein Account</Link>
          </li>
          <li onClick={setActive}>
            <Link to="/admin">Admin</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default Navigation;
