import {IEventWrite} from "../../types/IEvent";
import {GetCsrfToken, SetCsrfToken} from "../../util/UpdateToken";
import {MessageAlert} from "../../util/MessageAlert";

// 행사 상세 조회
export const getEvent = async (eventId: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/${eventId}`)
        .then((res) => res.json())
        .then(res => res.successResponseDTO.data);
}

// 행사 전체 조회
export const getEvents = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events`)
        .then((res) => res.json())
        .then((res) => res.successResponseDTO.data);
}

// 메인 페이지 행사 조회
export const getMainEvents = async () => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/top10`)
        .then(res => res.json())
        .then(res => res.successResponseDTO.data)
        .catch(() => MessageAlert("error", "조회 실패", null))
}

// 카테고리 별 행사 조회
export const getCategoryEvents = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/category/${data}`)
        .then(res => res.json())
        .then(res => res.successResponseDTO.data);
}

// 키워드 별 행사 조회
export const getKeywordEvents = async (data: string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/search?keyword=${data}`)
        .then(res => res.json())
        .then(res => res.successResponseDTO.data);
}

// 행사 주최
export const postEvent = async (data: FormData) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/secret/events`, {
        method: "POST",
        credentials: "include",
        headers: {"X-Csrf-Token": GetCsrfToken()!},
        body: data,
    })
        .then((res) => {
            SetCsrfToken(res);
            return res.json();
        })
        .catch(res => SetCsrfToken(res));
}

export const deleteEvent = async (data: number) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/secret/events/${data}`, {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    })
        .then((res) => res.status);
}