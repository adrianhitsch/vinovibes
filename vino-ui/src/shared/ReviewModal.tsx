import { Calendar } from 'primereact/calendar';
import { Chips } from 'primereact/chips';
import { InputNumber } from 'primereact/inputnumber';
import { Mention } from 'primereact/mention';
import { Rating } from 'primereact/rating';
import React, {
  BaseSyntheticEvent,
  useState,
  useEffect,
  useReducer,
  useRef,
  LegacyRef,
} from 'react';
import LocationSwitch from './LocationSwitch';
import '../styles/reviewModal.css';
import LocationChip from './LocationChip';
import CharacteristicsChip from './CharacteristicsChip';
import useApiFetch from '../wrapper/apiFetch';
import toast from 'react-hot-toast';
import { useLocation } from 'react-router-dom';

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
  priceType: 'RESTAURANT' | 'STORE';
}

interface postBody {
  wineId: number;
  value: number;
  userComment: string;
  vintage: string;
  price: number;
  priceType: 'RESTAURANT' | 'STORE';
}

const ReviewModal = ({ closeModal }: ReviewModalProps): JSX.Element => {
  const api = useApiFetch();
  const location = useLocation();

  const [data, setData] = useState<ReviewModalState>({
    rating: 0,
    price: { value: 0, currency: 'EUR' },
    date: new Date(),
    location: [],
    tags: [],
    message: '',
    priceType: 'RESTAURANT',
  });
  const [showModal, setShowModal] = useState(false);
  const characteristicsChip = useRef<Chips | null>(null);

  useEffect(() => {
    setShowModal(true);
  }, []);

  const _closeModal = () => {
    setShowModal(false);

    setTimeout(() => {
      if (closeModal) closeModal();
    }, 400);
  };

  const handleSave = async () => {
    const postBody: postBody = {
      wineId: location.state.id,
      value: data.rating,
      userComment: data.message,
      vintage: data.date.getFullYear().toString(),
      price: data.price.value,
      priceType: data.priceType,
    };

    if (data?.rating === 0) {
      toast.error('Bitte Sterne vergeben');
      return;
    }

    if (location.state.id === undefined) return;

    await api('/rating/create', {
      body: JSON.stringify(postBody),
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    }).catch((e) => toast.error(e.message));

    _closeModal();
  };

  return (
    <>
      <div className={`modal ${showModal ? 'show' : ''}`}>
        <div className="modal-header">
          <h1>Wein getrunken</h1>
          <button type="button" className="button transparent" onClick={_closeModal}>
            <span className="icon icon-close"></span>
            {''}
          </button>
        </div>
        <div className="modal-body">
          <div className="wrapper rating">
            <div className="left">
              <label htmlFor="rating">
                Bewertung
                <span className="mandatory">*</span>
              </label>
              <Rating
                value={data.rating}
                onChange={(e: any) =>
                  setData((prevState) => ({ ...prevState, rating: e.target.value }))
                }
                cancel={false}
                onIcon={<span className="icon icon-star-filled" />}
                offIcon={<span className="icon icon-star-white" />}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="left">
              <label htmlFor="rating" className="rating">
                Preis
              </label>
              <div className="switch-container">
                <LocationSwitch
                  onChange={(state: any) =>
                    setData((prevState) => ({
                      ...prevState,
                      priceType: state,
                    }))
                  }
                />
              </div>
              <InputNumber
                inputId="currency-germany"
                value={data.price.value}
                onValueChange={(e: any) =>
                  setData((prevState) => ({
                    ...prevState,
                    price: {
                      ...prevState.price,
                      value: e.target.value,
                    },
                  }))
                }
                mode="currency"
                currency="EUR"
                locale="de-DE"
              />
            </div>
            <div className="right">
              <label htmlFor="rating">Jahrgang</label>
              <Calendar
                view="year"
                dateFormat="yy"
                style={{ width: '100%' }}
                value={data.date}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, date: e.value }))}
                maxDate={new Date()}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Wo getrunken/gekauft?</label>
              <Chips
                value={data.location}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, location: e.value }))}
                itemTemplate={LocationChip}
                allowDuplicate={false}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Eigenschaften</label>
              <Chips
                ref={characteristicsChip}
                value={data.tags}
                onChange={(e: any) => setData((prevState) => ({ ...prevState, tags: e.value }))}
                // itemTemplate={(i) => CharacteristicsChip(i, data.tags)}
                allowDuplicate={false}
              />
            </div>
          </div>
          <div className="wrapper">
            <div className="full">
              <label htmlFor="rating">Kommentar</label>
              <Mention
                value={data.message}
                onChange={(e: BaseSyntheticEvent) =>
                  setData((prevState) => ({ ...prevState, message: e.target.value }))
                }
                rows={5}
                cols={40}
                autoResize
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
