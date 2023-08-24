import { atom } from 'recoil';

export const userState = atom({
    key: "userState",
    default: {
        email: "a@a.com",
        password: "a",
        passwordConfirm: "",
        name: "",
        phone: "",
        address: "",
        isHost: false,
    },
})