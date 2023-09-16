import {IChangePW, ILogin, ISignup} from "../../types/IUser";

export const postSignupEmailValid = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/email`,{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then(res => res.json())
        .catch(res => console.error(res));
}

export const postSignupUser = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/me/user`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}
export const postSignupHost = async (data: ISignup) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/me/host`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json())
        .catch(res => console.error(res));
}

export const postLogin = async (data: ILogin) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/login`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.json());
}

export const postLogout = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/logout`,{
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
    })
        .then(res => res.status);
}

export const getProfile = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/user/secret/users/me`,{
        method: "GET",
    })
        .then((res) => res.json())
        .then((res) => res.successResponseDTO.data);
}

export const patchChangePassword = async (data: IChangePW) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/secret/changePW`,{
        method: "PATCH",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then((res) => res.status);
}

export const deleteAccount = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/secret/me`,{
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    })
        .then((res) => res.status);
}