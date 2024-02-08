import React, { useEffect, useRef, useState } from 'react';

export interface RatingProps {
  stars: number;
  size?: 'small' | 'default';
}

const StarRating = ({ stars, size }: RatingProps) => {
  size = size || 'default';
  const [ratingArray, setRatingArray] = useState<number[]>([]);
  const id = useRef(0);

  useEffect(() => {
    const ratingArray = [];
    const fullStars = Math.floor(stars);
    const halfStars = stars - fullStars >= 0.5 ? 1 : 0;
    const emptyStars = 5 - fullStars - halfStars;

    for (let i = 0; i < fullStars; i++) {
      ratingArray.push(1);
    }
    if (halfStars === 1) {
      ratingArray.push(0.5);
    }
    for (let i = 0; i < emptyStars; i++) {
      ratingArray.push(0);
    }

    setRatingArray(ratingArray);
  }, [stars]);

  return (
    <div className={`product-rating ${size}`}>
      {ratingArray.map((rating) => (
        <div className="product-rating-star " key={id.current++}>
          <span
            className={`icon ${size}  ${
              rating === 1 ? 'icon-star-filled' : rating === 0.5 ? 'icon-star-half' : 'icon-star'
            }`}
          ></span>
        </div>
      ))}
      <span className="number">{stars?.toFixed(1) || '0.0'}</span>
    </div>
  );
};

export default StarRating;
