import React, { useContext, useEffect, useId, useRef, useState } from 'react';
import '../styles/tableView.css';
import useApiFetch from '../wrapper/apiFetch';
import productImage from '../assets/pictures/product.jpg';
import { useNavigate } from 'react-router';
import { HeaderContext } from '../layout/contentHeader';
import StarRating from '../shared/StarRating';
import { WineRequest } from '../types/wineRequest';
import toast from 'react-hot-toast';

type productType = {
  country: string;
  description: string;
  id: number;
  name: string;
  producer: string;
  rating: number;
  region: string;
  type: string;
  vintage: number;
};

type productProps = {
  productInfo: productType;
};

const Product = ({ productInfo }: productProps) => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/vine-detail');
  };

  return (
    <div className="product" key={productInfo.id} onClick={handleClick}>
      <div className="product-image">
        <img src={productImage} alt="" />
      </div>
      <div className="product-info">
        <StarRating stars={productInfo.rating} size="small" />
        <div className="product-name">{productInfo.name}</div>
        <div className="product-country">{productInfo.country}</div>
      </div>
    </div>
  );
};

const TableView = () => {
  const [products, setProducts] = useState([]);
  const { setHeaderContent } = useContext(HeaderContext);
  const apiFetch = useApiFetch();

  useEffect(() => {
    setHeaderContent({
      header: 'Alle Weine',
      text: 'Hier findest du alle Weine.',
      buttons: [],
    });

    getProducts();

    return () => {
      setHeaderContent({
        header: '',
        text: '',
        buttons: [],
      });
    };
  }, []);

  const getProducts = async () => {
    const requestBody: WineRequest = {
      skip: 0,
      take: 20,
      sortBy: '',
      sortDirection: '',
      type: '',
    };

    await apiFetch('/wine/wines', { method: 'POST', body: JSON.stringify(requestBody) })
      .then((data) => data.json())
      .then((data) => setProducts(data))
      .catch(() => toast.error('Weine konnten nicht geladen werden'));
  };

  return (
    <div className="content-container">
      <div className="content-card table">
        <div className="table-filter"></div>
        <div className="table-content">
          {products.map((product) => (
            <Product productInfo={product} />
          ))}
        </div>
      </div>
    </div>
  );
};
export default TableView;
