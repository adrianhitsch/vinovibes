export interface storeType {
    user: {
        email: null | string,
        sessionEnd: number,
        token: null | string,
        newUser: boolean,
        firstName: null | string,
        lastName: null | string,
    },
}