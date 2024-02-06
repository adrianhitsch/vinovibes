import React, { useContext, useEffect, useState } from 'react';
import { HeaderContext } from '../layout/contentHeader';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Chips } from 'primereact/chips';
import { Mention } from 'primereact/mention';
import '../styles/createVino.css';
import { FileUpload, FileUploadFilesEvent } from 'primereact/fileupload';

const CreateVino = () => {
  const { setHeaderContent } = useContext(HeaderContext);
  const [picture, setPicture] = useState(null);

  useEffect(() => {
    setHeaderContent({
      header: 'Wein erstellen',
      text: 'Erstelle einen Wein',
      buttons: [],
    });

    return () => {
      setHeaderContent({
        header: '',
        text: '',
        buttons: [],
      });
    };
  }, []);

  const emptyTemplate = () => {
    return (
      <div className="flex align-items-center flex-column">
        <i
          className="pi pi-image mt-3 p-5"
          style={{
            fontSize: '5em',
            borderRadius: '50%',
            backgroundColor: 'var(--surface-b)',
            color: 'var(--surface-d)',
          }}
        ></i>
        <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
          Drag and Drop Image Here
        </span>
      </div>
    );
  };

  const handleFileUpload = (event: any) => {
    setPicture(event.files[0].objectURL);
  };

  return (
    <div className="content-container">
      <div className="content-card create-vino">
        <div>
          <div className="wrapper">
            <label htmlFor="name">Kategorien</label>
            <Dropdown />
          </div>

          <div className="wrapper">
            <label htmlFor="name">Herkunftsland</label>
            <Dropdown />
          </div>
          <button className="button secondary">zurück</button>
        </div>
        <div>
          <div className="wrapper">
            <label htmlFor="name">Name</label>
            <InputText />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Weingut</label>
            <InputText />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Rebsorte(n)</label>
            <Chips />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Beschreibung</label>
            <Mention />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Eigenschaften</label>
            <Chips />
          </div>
        </div>
        <div>
          <div className="wrapper image">
            <label htmlFor="name">Bild</label>
            {picture && <img src={picture} />}

            <FileUpload
              mode="basic"
              accept="image/*"
              //   emptyTemplate={emptyTemplate}
              //   onUpload={handleFileUpload}
              onSelect={handleFileUpload}
            />
          </div>
          <button className="button primary">Wein erstellen</button>
        </div>
      </div>
    </div>
  );
};

export default CreateVino;
