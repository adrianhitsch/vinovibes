import React, { BaseSyntheticEvent, useContext, useEffect, useState } from 'react';
import StarRating from '../shared/StarRating';
import '../styles/detailPage.css';
import detailImage from '../assets/pictures/product.jpg';
import { HeaderContext } from '../layout/contentHeader';
import Map from '../shared/Map';
import Rating from '../shared/Rating';
import profileImg from '../assets/pictures/profile.png';
import ReviewModal from '../shared/ReviewModal';
import useApiFetch from '../wrapper/apiFetch';
import toast from 'react-hot-toast';
import { useLocation } from 'react-router-dom';

interface DetailDataProps {
  country: string;
  description: string;
  id: number;
  name: string;
  producer: string;
  rating: number;
  region: string;
  type: string;
  vintage: number;
  restaurantPrice: number;
  storePrice: number;
  imageUrl: string;
}

const DetailPage = () => {
  const api = useApiFetch();
  const location = useLocation();
  const { setHeaderContent } = useContext(HeaderContext);
  const [detailData, setDetailData] = useState<DetailDataProps>();
  const [showModal, setShowModal] = useState(false);
  const tags = [
    { name: 'kräftig', count: 4 },
    { name: 'säurehaltig', count: 2 },
  ];

  useEffect(() => {
    setHeaderContent({
      header: 'DetailView',
      text: 'Hier kann dein Produkt stehen',
      buttons: [],
    });

    // scroll to top of the page
    document.getElementsByClassName('content')[0].scrollTop = 0;

    getVinoDetail();
  }, [setHeaderContent]);

  const getVinoDetail = async () => {
    await api(`/wine/${location.state?.id}`, { method: 'GET' })
      .then((data) => {
        if (data.status === 200) {
          return data.json();
        }
        throw new Error('Wine not found');
      })
      .then((data) => setDetailData(data))
      .catch((e) => toast.error(e.message));
  };

  const handleSave = (e: BaseSyntheticEvent) => {
    e.target.classList.add('animate');
    setTimeout(() => {
      e.target.classList.remove('animate');
    }, 500);
  };

  // TODO https://primereact.org/dataview/ check if this is a good solution for grid

  return (
    <>
      {showModal && <ReviewModal closeModal={() => setShowModal(false)} />}
      <div className="content-container">
        <div className="content-card detail">
          <div className="detail-image">
            <img src={detailData?.imageUrl} alt="" />
          </div>
          <div className="detail-info">
            <StarRating stars={detailData?.rating || 0} />
            <h1 className="detail-name">{detailData?.name}</h1>
            <div className="detail-country">{detailData?.country}</div>
            <div className="detail-producer">{detailData?.producer}</div>
            <div className="detail-tags">
              {tags.map((tag) => (
                <div className="tag">
                  <div className="tag-name">{tag.name}</div>
                  <div className="tag-count">{tag.count}</div>
                </div>
              ))}
            </div>
            <div className="detail-description">{detailData?.description}</div>
            <div className="detail-price">
              {detailData?.restaurantPrice && (
                <div className="price">
                  <span className="icon icon-restaurant big"></span>
                  <span>⌀ {detailData?.restaurantPrice.toFixed(2)} €</span>
                </div>
              )}
              {detailData?.storePrice && (
                <div className="price">
                  <span className="icon icon-store big"></span>
                  <span>⌀ {detailData?.storePrice.toFixed(2)} €</span>
                </div>
              )}
            </div>
            <div className="button-container" onClick={handleSave}>
              <button className="button secondary">
                <span className="icon icon-heart"></span>
                Wein merken
              </button>
              <button className="button primary" onClick={() => setShowModal(true)}>
                <span className="icon icon-check"></span>
                Wein getrunken
              </button>
              <p>Zuletzt von dir getrunken am: 23.11.2023</p>
            </div>
          </div>
        </div>
        <div className="content-card padding small">
          <h2>Bewertung</h2>
          <Rating id={detailData?.id} />
        </div>
        <div className="content-card padding small">
          <h2>Getrunken von</h2>
          <div className="user-list">
            <div className="user-icon">
              <img src={profileImg} alt="" />
              <span>username</span>
            </div>
            <div className="user-icon">
              <img src={profileImg} alt="" />
              <span>username</span>
            </div>
          </div>
        </div>
        <div className="content-card padding small">
          <h2>Wo zu finden?</h2>
          <Map />
        </div>
      </div>
    </>
  );
};
export default DetailPage;
