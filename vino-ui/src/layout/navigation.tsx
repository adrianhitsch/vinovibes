import { Toast } from "primereact/toast";
import React, { useEffect } from "react";
import toast, { Toaster } from "react-hot-toast";
import { Button } from "primereact/button";
import { Link } from "react-router-dom";

const Navigation = () => {
  const setActive = (e: any) => {
    const elements = document.querySelectorAll("a");
    console.log(elements);
    elements.forEach((el) => {
      el.classList.add("active");
    });
    e.target.classList.add("active");
  };

  return (
    <div className="main-nav">
      <div className="logo-container">
        <img src="vinoVibes.png" alt="VinoLogo" className="vino-logo" />
      </div>
      <nav>
        <ul>
          <li onClick={setActive}>
            <Link to="/">Dashboard</Link>
          </li>
          <li>
            <li onClick={setActive}>
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
