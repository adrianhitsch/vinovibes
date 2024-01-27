import React, { useContext, useEffect } from 'react';
import StarRating from '../shared/StarRating';
import '../styles/detailPage.css';
import detailImage from '../assets/pictures/product.jpg';
import { HeaderContext } from '../layout/contentHeader';
import Map from '../shared/Map';
import Rating from '../shared/Rating';
import profileImg from '../assets/pictures/profile.png';

const DetailPage = () => {
  const { setHeaderContent } = useContext(HeaderContext);
  const tags = [
    { name: 'testName', count: 2 },
    { name: 'testName', count: 2 },
  ];

  useEffect(() => {
    setHeaderContent({
      header: 'DetailView',
      text: 'Hier kann dein Produkt stehen',
      buttons: [],
    });
  }, [setHeaderContent]);

  return (
    <div className="content-container">
      <div className="content-card detail">
        <div className="detail-image">
          <img src={detailImage} alt="" />
        </div>
        <div className="detail-info">
          <StarRating stars={4} />
          <h1 className="detail-name">Name</h1>
          <div className="detail-country">Country</div>
          <div className="detail-year">Year</div>
          <div className="detail-tags">
            {tags.map((tag) => (
              <div className="tag">
                <div className="tag-name">{tag.name}</div>
                <div className="tag-count">{tag.count}</div>
              </div>
            ))}
          </div>
          <div className="detail-description">
            In einem idyllischen Weinberg, umgeben von sanften Hügeln und einer malerischen
            Landschaft, gedeiht unser exquisiter Wein mit Hingabe und Sorgfalt. Die Trauben werden
            von erfahrenen Winzern liebevoll ausgewählt und handverlesen, um eine einzigartige
            Fusion von Aromen und Geschmacksnuancen zu schaffen. Dieser edle Tropfen verführt mit
            einer betörenden Nase von frischen Früchten und einer harmonischen Balance von Säure und
            Süße. Am Gaumen entfaltet er seine vollmundige Eleganz, begleitet von feinen Tanninen
            und einem lang anhaltenden Abgang. Genießen Sie diesen Wein als Begleiter zu besonderen
            Anlässen oder lassen Sie sich von seiner Raffinesse im Alltag verführen. Ein wahrer
            Genuss für anspruchsvolle Gaumen, der die Kunst der Weinherstellung in jeder Flasche
            widerspiegelt.
          </div>
          <div className="detail-price">
            <div className="price">
              <span className="icon icon-restaurant big"></span>
              <span>⌀ 23,65 €</span>
            </div>
            <div className="price">
              <span className="icon icon-shop big"></span>
              <span>⌀ 23,65 €</span>
            </div>
          </div>
          <div className="button-container">
            <button className="button secondary">
              <span className="icon icon-heart"></span>
              Wein merken
            </button>
            <button className="button primary">
              <span className="icon icon-check"></span>
              Wein getrunken
            </button>
            <p>Zuletzt von dir getrunken am: 23.11.2023</p>
          </div>
        </div>
      </div>
      <div className="content-card padding small">
        <h2>Bewertung</h2>
        <Rating />
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
  );
};
export default DetailPage;
