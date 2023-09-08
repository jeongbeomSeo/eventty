import {IChangePW, ILogin, IMyInfo, ISignup} from "../../types/IUser";

export const postSignupEmailValid = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/email`,{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then(res => res.json())
        .catch(res => console.error(res));
}

export const postSignupUser = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/me/user`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}
export const postSignupHost = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/me/host`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}

export const postLogin = async (data: ILogin) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const putChangePW = async (data: IChangePW) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/changePW`,{
        method: "PUT",
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const getMyInfo = async (refreshToken: string) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/user/myInfo`,{
        method: "GET",
    })
        .then((res) => res.json());
}

export const patchMyInfo = async (data: IMyInfo) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/user/myinfo`,{
        method: "PATCH",
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const patchDeleteAccount = async () => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/auth/myInfo`,{ //  왜 myInfo?
        method: "PATCH",
    })
        .then((res) => res.json());
}