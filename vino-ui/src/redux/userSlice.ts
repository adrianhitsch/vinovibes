import {createSlice} from '@reduxjs/toolkit';

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        user: null,
        sessionEnd: 0,
        token: null,
    },
    reducers: {
        login: (state, action) => {
            // now + 1 Hour
            console.log(action);
            state.sessionEnd = new Date().getTime() + 3600000;
            state.token = action.payload;
        },
        logout: (state) => {
            state.sessionEnd = 0;
            state.token = null;
        },
    },
});

export const {login, logout} = userSlice.actions;
export default userSlice.reducer;