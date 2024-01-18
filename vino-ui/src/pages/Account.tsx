import React, { useContext, useEffect, useRef } from 'react';
import { ButtonType, HeaderContext } from '../layout/contentHeader';

const Account = () => {
  const { setHeaderContent } = useContext(HeaderContext);

  const headerButtons = useRef<Array<ButtonType>>();

  useEffect(() => {
    console.log(headerButtons.current);
    setHeaderContent({
      header: 'Mein Profil',
      text: 'Details zu meinem Profil',
      buttons: [
        {
          text: 'Bearbeiten',
          onClick: () => handleProfileEdit(),
          icon: 'edit-gradient',
          type: 'secondary',
        },
      ],
    });

    return () => {
      setHeaderContent({
        header: '',
        text: '',
        buttons: [],
      });
    };
  }, [setHeaderContent]);

  const handleProfileEdit = () => {
    console.log('test');
    setHeaderContent({
      header: 'Mein Profil',
      text: 'Details zu meinem Profil',
      buttons: [
        {
          text: 'Speichern',
          onClick: () => handleProfileSave(),
          icon: 'tik-mark-gradient',
          type: 'secondary',
        },
        { type: 'close', onClick: () => stopEditing() },
      ],
    });
  };

  const handleProfileSave = () => {
    console.log('save');
    stopEditing();
  };

  const stopEditing = () => {
    setHeaderContent({
      header: 'Mein Profil',
      text: 'Details zu meinem Profil',
      buttons: [
        {
          text: 'Bearbeiten',
          onClick: () => handleProfileEdit(),
          icon: 'edit-gradient',
          type: 'secondary',
        },
      ],
    });
  };

  return (
    <div className="content-container">
      <div className="content-card"></div>
    </div>
  );
};
export default Account;
