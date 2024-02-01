import { Calendar } from 'primereact/calendar';
import { Chips } from 'primereact/chips';
import { InputNumber } from 'primereact/inputnumber';
import { Mention } from 'primereact/mention';
import { Rating } from 'primereact/rating';
import React, { BaseSyntheticEvent, useState, useEffect } from 'react';
import LocationSwitch from './LocationSwitch';

const style = `
.backdrop {
  opcaity: 0;
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
}

.backdrop.show{
  opacity: 1;
  transition: opacity 0.3s;F
}

.modal {
  opcaity: 0;
  border-radius: 25px;  
  position: fixed;
  max-width: 700px;
  width: 80%;
  height: 860px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, 100%);
  background: #273039;
  z-index: 101;
  padding: 32px 40px;
  display: flex;
  flex-direction: column;
  opacity: 0;
  transition: opacity 0.3s, transform 0.3s ease-out;
}

.modal.show {
  opacity: 1;
  transform: translate(-50%, -50%) ;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.modal-header {
  h1 {
    font-size: 32px;
    color: #fff;
    margin: 0;
  }
  .icon {
    height: 32px;
    width: 32px;
    color: #fff;
  }
}

.modal-footer {
  margin-top: auto;
  height: 60px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.modal-footer button.flex {
  display: flex;
  align-items: center;
}

.modal-body {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.wrapper {
  width: 100%;
  .p-component, .p-inputtext   {
    width: 100%;
  }

  & > div {
    display: flex;
    flex-direction: column;
    gap: 5px;  
    height: 100%;
    justify-content: flex-end;
  }

  .left {
    float: left;
    width: calc(50% - 36px);
  }
  .right {
    float: right;
    width: 50%;
  }
  .full {
    width: 100%;
  }
}
`;

type ReviewModalProps = {
  closeModal?: () => void;
};

interface ReviewModalState {
  rating: number;
  price: { value: number; currency: string };
  date: Date;
  location: string[];
  tags: string[];
  message: string;
}

const ReviewModal = ({ closeModal }: ReviewModalProps): JSX.Element => {
  const [data, setData] = useState<ReviewModalState>({
    rating: 0,
    price: { value: 0, currency: 'EUR' },
    date: new Date(),
    location: [],
    tags: [],
    message: '',
  });
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    setShowModal(true);
  }, []);

  const _closeModal = () => {
    if (closeModal) closeModal();
  };

  const handleSave = () => {
    _closeModal();
  };

  return (
    <>
      <style>{style}</style>
      <div className={`modal ${showModal ? 'show' : ''}`}>
        <div className="modal-header">
          <h1>Wein getrunken</h1>
          <button type="button" className="button transparent" onClick={_closeModal}>
            <span className="icon icon-close"></span>
            {''}
          </button>
        </div>
        <div className="modal-body">
          <div className="wrapper">
            <div className="left">
              <label htmlFor="rating">Bewertung</label>
              <Rating
                value={data.rating}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, rating: e.value }))}
                cancel={false}
                onIcon={<span className="icon icon-star-filled" />}
                offIcon={<span className="icon icon-star" />}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="left">
              <label htmlFor="rating">Preis</label>
              <LocationSwitch />
              <InputNumber
                inputId="currency-germany"
                value={data.price.value}
                onValueChange={(e: any) =>
                  setData((prevState) => ({
                    ...prevState,
                    price: {
                      ...prevState.price,
                      value: e.value,
                    },
                  }))
                }
                mode="currency"
                currency="EUR"
                locale="de-DE"
              />
            </div>
            <div className="right">
              <label htmlFor="rating">Bewertung</label>
              <Calendar
                dateFormat="yy"
                view="year"
                value={data.date}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, date: e.value }))}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Wo getrunken/gekauft?</label>
              <Chips
                value={data.location}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, location: e.value }))}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Eigenschaften</label>
              <Chips
                value={data.tags}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, tags: e.value }))}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Eigenschaften</label>
              <Mention
                value={data.message}
                onChange={(e: BaseSyntheticEvent) =>
                  setData((prevState) => ({ ...prevState, message: e.target.value }))
                }
                rows={5}
                cols={40}
              />
            </div>
          </div>
        </div>
        <div className="modal-footer">
          <button type="button" className="button secondary" onClick={_closeModal}>
            Zurück
          </button>
          <button type="button" className="button primary flex" onClick={handleSave}>
            <span className="icon icon-check"></span>
            Wein getrunken
          </button>
        </div>
      </div>
      <div className={`backdrop ${showModal ? 'show' : ''}`}></div>
    </>
  );
};

export default ReviewModal;
export type { ReviewModalProps };
