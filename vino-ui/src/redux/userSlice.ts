import {createSlice} from '@reduxjs/toolkit';

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        user: null,
        authenticated: false,
        sessionEnd: 0,
    },
    reducers: {
        login: (state) => {
            state.authenticated = true;
            // now + 1 Hour
            state.sessionEnd = new Date().getTime() + 3600000;
        },
        logout: (state) => {
            state.authenticated = false;
            state.sessionEnd = 0;
        },
    },
});

export const {login, logout} = userSlice.actions;
export default userSlice.reducer;