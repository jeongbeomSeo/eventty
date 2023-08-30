import {IChangePW, ILogin, IMyInfo, ISignup} from "../../types/IUser";

const GATEWAY = "";
const MY_HEADERS = new Headers({"Content-Type": "application/json"},);

export const postSignup = (data: ISignup) => {
    return fetch(`${GATEWAY}/api/auth/register`, {
        method: "POST",
        headers: MY_HEADERS,
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const postLogin = (data: ILogin) => {
    return fetch(`${GATEWAY}/api/auth/login`, {
        method: "POST",
        headers: MY_HEADERS,
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const putChangePW = (data: IChangePW) => {
    return fetch(`${GATEWAY}/api/auth/changePW`,{
        method: "PUT",
        headers: MY_HEADERS,
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const getMyInfo = () => {
    return fetch(`${GATEWAY}/api/user/myInfo`)
        .then((res) => res.json());
}

export const patchMyInfo = (data: IMyInfo) => {
    return fetch(`${GATEWAY}/api/user/myinfo`,{
        method: "PATCH",
        headers: MY_HEADERS,
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const patchDeleteAccount = () => {
    return fetch(`${GATEWAY}/api/auth/myInfo`,{ //  왜 myInfo?
        method: "PATCH",
    })
        .then((res) => res.json());
}