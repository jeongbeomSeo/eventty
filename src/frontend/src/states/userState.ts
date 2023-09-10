import { atom } from 'recoil';

export const userState = atom({
    key: "userState",
    default: {
        email: "a@a.com",
        password: "a",
        passwordConfirm: "",
        name: "",
        phone: "",
        nickname: "",
        birth: new Date(Date.now()),
        address: "",
        isHost: false,
    },
})