import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    email: null,
    firstName: null,
    lastName: null,
    sessionEnd: 0,
    token: null,
    newUser: false,
  },
  reducers: {
    login: (state, action) => {
      state.sessionEnd = new Date().getTime() + 3600000;
      state.token = action.payload.token;
      state.email = action.payload.email;
      state.firstName = action.payload.firstName;
      state.lastName = action.payload.lastName;
    },
    logout: (state) => {
      state.sessionEnd = 0;
      state.token = null;
      state.email = null;
      state.firstName = null;
      state.lastName = null;
    },
    registerUser: (state, action) => {
      state.sessionEnd = new Date().getTime() + 3600000;
      state.token = action.payload.token;
      state.email = action.payload.email;
      state.firstName = action.payload.firstName;
      state.lastName = action.payload.lastName;
      state.newUser = true;
    },
    setEmail: (state, action) => {
      state.email = action.payload;
    },
    resetNewUser: (state) => {
      state.newUser = false;
    },
  },
});

export const { login, logout, registerUser, resetNewUser, setEmail } = userSlice.actions;
export default userSlice.reducer;
