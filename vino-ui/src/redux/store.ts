
import userReducer from './userSlice';
import storage from 'redux-persist/es/storage';
import { persistReducer, persistStore } from 'redux-persist';
import { combineReducers, createStore } from '@reduxjs/toolkit';

const persistConfig = {
    key: 'root',
    storage,
  };

  const combinedReducers = combineReducers
    ({
        user: userReducer,
    });

  const persistedReducer = persistReducer(persistConfig, combinedReducers);
  
  export const store = createStore(persistedReducer);

  export const persistor = persistStore(store);