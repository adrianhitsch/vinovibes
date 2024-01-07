import {createSlice} from '@reduxjs/toolkit';

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        email: null,
        sessionEnd: 0,
        token: null,
    },
    reducers: {
        login: (state, action) => {
            // now + 1 Hour
            console.log(action);
            state.sessionEnd = new Date().getTime() + 3600000;
            state.token = action.payload.token;
            state.email = action.payload.email;
        },
        logout: (state) => {
            state.sessionEnd = 0;
            state.token = null;
            state.email = null;
        },
    },
});

export const {login, logout} = userSlice.actions;
export default userSlice.reducer;