import React, { useContext, useEffect, useState } from 'react';
import { HeaderContext } from '../layout/contentHeader';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Chips } from 'primereact/chips';
import { Mention } from 'primereact/mention';
import '../styles/createVino.css';
import { FileUpload, FileUploadFilesEvent } from 'primereact/fileupload';
import '/node_modules/flag-icons/css/flag-icons.min.css';
import useApiFetch from '../wrapper/apiFetch';
import toast from 'react-hot-toast';
import { Calendar } from 'primereact/calendar';

const countries = [
  { name: 'Australien', code: 'AU' },
  { name: 'Brasilien', code: 'BR' },
  { name: 'China', code: 'CN' },
  { name: 'Ägypten', code: 'EG' },
  { name: 'Frankreich', code: 'FR' },
  { name: 'Deutschland', code: 'DE' },
  { name: 'Indien', code: 'IN' },
  { name: 'Japan', code: 'JP' },
  { name: 'Spanien', code: 'ES' },
  { name: 'Vereinigte Staaten', code: 'US' },
  { name: 'Vereinigtes Königreich', code: 'GB' },
  { name: 'Russland', code: 'RU' },
  { name: 'Schweiz', code: 'CH' },
  { name: 'Österreich', code: 'AT' },
  { name: 'Italien', code: 'IT' },
  { name: 'Niederlande', code: 'NL' },
  { name: 'Belgien', code: 'BE' },
  { name: 'Dänemark', code: 'DK' },
  { name: 'Finnland', code: 'FI' },
  { name: 'Griechenland', code: 'GR' },
  { name: 'Irland', code: 'IE' },
  { name: 'Norwegen', code: 'NO' },
  { name: 'Portugal', code: 'PT' },
  { name: 'Schweden', code: 'SE' },
  { name: 'Polen', code: 'PL' },
  { name: 'Türkei', code: 'TR' },
  { name: 'Ukraine', code: 'UA' },
  { name: 'Kanada', code: 'CA' },
  { name: 'Mexiko', code: 'MX' },
  { name: 'Argentinien', code: 'AR' },
  { name: 'Chile', code: 'CL' },
  { name: 'Kolumbien', code: 'CO' },
  { name: 'Peru', code: 'PE' },
  { name: 'Venezuela', code: 'VE' },
  { name: 'Äthiopien', code: 'ET' },
  { name: 'Nigeria', code: 'NG' },
  { name: 'Südafrika', code: 'ZA' },
  { name: 'Südkorea', code: 'KR' },
  { name: 'Thailand', code: 'TH' },
  { name: 'Vietnam', code: 'VN' },
  { name: 'Saudi-Arabien', code: 'SA' },
  { name: 'Vereinigte Arabische Emirate', code: 'AE' },
];

const categories = [
  { name: 'Rotwein', code: 'RED' },
  { name: 'Weißweint', code: 'WHITE' },
  { name: 'Rosé', code: 'ROSE' },
  { name: 'Sekt', code: 'SPARKLING' },
];
const selectedCountryTemplate = (option: any, props: any) => {
  if (option) {
    return (
      <div className="flex align-items-center">
        <span
          className={`fi fi-${option.code.toLowerCase()}`}
          style={{ marginRight: '15px' }}
        ></span>
        <span>{option.name}</span>
      </div>
    );
  }

  return <span>{props.placeholder}</span>;
};

const countryOptionTemplate = (option: any) => {
  return (
    <div className="flex align-items-center">
      <div className="flex align-items-center">
        <span
          className={`fi fi-${option.code.toLowerCase()}`}
          style={{ marginRight: '15px' }}
        ></span>
        <span>{option.name}</span>
      </div>
    </div>
  );
};

interface vineData {
  category: { name: string; code: string };
  country: { name: string; code: string };
  name: string;
  winery: string;
  grape: string[];
  description: string;
  properties: string[];
  image: string;
  vintage: Date;
}

const CreateVino = () => {
  const { setHeaderContent } = useContext(HeaderContext);
  const [picture, setPicture] = useState(null);
  const [vineData, setVineData] = useState<vineData | null>(null);
  const api = useApiFetch();

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

  const handleSave = () => {
    const postBody = {
      type: vineData?.category.code,
      country: vineData?.country.name,
      name: vineData?.name,
      producer: vineData?.winery,
      description: vineData?.description,
      region: 'test',
      vintage: vineData?.vintage.getFullYear().toString(),
    };
    api('/wine/create', {
      body: JSON.stringify(postBody),
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
    })
      .then((data) => {
        if (data.status === 200) {
          return data.json();
        }
        throw new Error('Wine not created');
      })
      .then(() => toast.success('Wein wurde erstellt'))
      .catch((e) => toast.error(e.message));
  };

  const handleFileUpload = (event: any) => {
    setPicture(event.files[0].objectURL);
  };

  return (
    <div className="content-container">
      <div className="content-card create-vino">
        <div>
          <div className="wrapper">
            <label htmlFor="name">Kategorie</label>
            <Dropdown
              options={categories}
              optionLabel="name"
              value={vineData?.category}
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, category: e.value }))
              }
            />
          </div>

          <div className="wrapper">
            <label htmlFor="name">Herkunftsland</label>
            <Dropdown
              value={vineData?.country}
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, country: e.value }))
              }
              options={countries}
              optionLabel="name"
              filter
              valueTemplate={selectedCountryTemplate}
              itemTemplate={countryOptionTemplate}
              className="w-full md:w-14rem"
            />
          </div>
          <button className="button secondary">zurück</button>
        </div>
        <div>
          <div className="wrapper">
            <label htmlFor="name">Name</label>
            <InputText
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, name: e.target.value }))
              }
            />
          </div>

          <div className="wrapper">
            <label htmlFor="name">Jahrgang</label>
            <Calendar
              view="year"
              dateFormat="yy"
              style={{ width: '100%' }}
              value={vineData?.vintage || new Date()}
              onChange={(e: any) =>
                setVineData((prevState: any) => ({ ...prevState, vintage: e.value }))
              }
              maxDate={new Date()}
            />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Weingut</label>
            <InputText
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, winery: e.target.value }))
              }
            />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Rebsorte(n)</label>
            <Chips
              value={vineData?.grape}
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, grape: e.target.value }))
              }
            />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Beschreibung</label>
            <Mention
              onChange={(e: any) =>
                setVineData((prevState: any) => ({ ...prevState, description: e.target.value }))
              }
            />
          </div>
          <div className="wrapper">
            <label htmlFor="name">Eigenschaften</label>
            <Chips
              value={vineData?.properties}
              onChange={(e) =>
                setVineData((prevState: any) => ({ ...prevState, properties: e.target.value }))
              }
            />
          </div>
        </div>
        <div>
          <label htmlFor="name">Bild</label>
          <div className="wrapper image">
            {picture && <img src={picture} />}

            <FileUpload
              mode="basic"
              accept="image/*"
              //   emptyTemplate={emptyTemplate}
              onUpload={handleFileUpload}
              onSelect={handleFileUpload}
            />
          </div>
          <button className="button primary" onClick={handleSave}>
            Wein erstellen
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateVino;
