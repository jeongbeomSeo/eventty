import {IChangePW, ILogin, ISignup, IUpdateUser, IUser} from "../../types/IUser";
import {GetCsrfToken, SetCsrfToken} from "../../util/UpdateToken";
import {redirect} from "react-router-dom";
import {CheckLogin} from "../../util/CheckLogin";
import {googleLogout} from "@react-oauth/google";

export const postSignupEmailValid = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/email`, {
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
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        });
}

export const postLogout = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/logout`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
    })
        .then(res => res.status)
        .catch(res => console.error(res));
}

export const getProfile = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/user/secret/users/me`, {
        method: "GET",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        })
        .then((res) => res.successResponseDTO.data)
        .catch(res => {
            SetCsrfToken(res);
            redirect("/login");
        });
}

export const postProfile = async (data: FormData) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/user/secret/users/me`, {
        method: "POST",
        credentials: "include",
        headers: {"X-Csrf-Token": GetCsrfToken()!},
        body: data,
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.status;
        })
        .catch(res => SetCsrfToken(res));
}

export const postChangePassword = async (data: IChangePW) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/changePW`, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json", "X-Csrf-Token": GetCsrfToken()!},
        body: JSON.stringify(data),
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.status;
        })
        .catch(res => SetCsrfToken(res));
}

export const deleteAccount = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/auth/secret/me`, {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    })
        .then((res) => res.status);
}