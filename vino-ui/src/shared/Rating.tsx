import React, { useEffect, useRef, useState } from 'react';
import StarRating from './StarRating';
import '../styles/rating.css';
import profileImg from '../assets/pictures/profile.png';
import useApiFetch from '../wrapper/apiFetch';
import toast from 'react-hot-toast';
interface RatingItemProps {
  id: number;
  value: number;
  userComment: string;
  vintage: string;
  price: number;
  priceType: string;
  user: string;
  wineId: number;
}
const RatingItem = ({ rating }: any): JSX.Element => {
  return (
    <div className="rating-item">
      <div className="user-icon">
        <img src={profileImg} alt="" />
        <span>username</span>
      </div>
      <div className="rating-container">
        <div className="rating-info">
          <StarRating stars={4} size="small" />
          <div className="rating-location">
            <span className="icon icon-location"></span>
            <span>Location</span>
          </div>
          <div className="rating-price">
            <span className="icon icon-restaurant"></span>
            <span>23,65 €</span>
          </div>
        </div>
        <div className="rating-text">
          Richtigen Schädel davon gehabt. Nie wieder! Wobei…eigentlich war er okay. Hat halt
          geballert. Richtigen Schädel davon gehabt. Nie wieder! Wobei…eigentlich war er okay. Hat
          halt geballert. Richtigen Schädel davon gehabt. Nie wieder! Wobei…eigentlich war er okay.
          Hat halt geballert. Kann sein … Mehr anzeigen
        </div>
        <div className="rating-date">am 07.12.2023</div>
      </div>
    </div>
  );
};

interface RatingProps {
  id?: number;
}

const Rating = ({ id }: RatingProps) => {
  const [ratings, setRating] = useState<Array<RatingItemProps>>([]);
  const api = useApiFetch();
  const lastId = useRef(id);

  useEffect(() => {
    if (id === lastId.current) return;

    getRatings();
    lastId.current = id;
  }, [id]);

  const getRatings = async () => {
    await api(`/ratings/${id}`, { method: 'GET' })
      .then((data) => {
        if (data.status === 200) {
          return data.json();
        }
        throw new Error('Ratings not found');
      })
      .then((data) => {
        setRating(data);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  return (
    <div className="rating">
      {ratings.map((rating) => {
        return <RatingItem rating={rating} />;
      })}
    </div>
  );
};
export default Rating;
