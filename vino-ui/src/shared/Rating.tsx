import React, { useEffect } from 'react';
import StarRating from './StarRating';
import '../styles/rating.css';
import profileImg from '../assets/pictures/profile.png';

const RatingItem = () => {
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
  id: number;
}

const Rating = ({ id }: RatingProps) => {
  const [ratings, setRating];
  useEffect(() => {
    console.log(id);
  }, [id]);

  return (
    <div className="rating">
      <RatingItem />
      <RatingItem />
    </div>
  );
};
export default Rating;
