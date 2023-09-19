// 행사 상세 조회
import {IEventWrite} from "../../types/IEvent";

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

// 카테고리 별 행사 조회
export const getCategoryEvents = async (data:string) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/category/${data}`)
        .then(res => res.json())
        .then(res => res.successResponseDTO.data)
}

// 행사 주최
export const postEvent = async (data: IEventWrite) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events`,{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    })
        .then(res => res.json())
}

export const deleteEvent = async (data: number) => {
    return await fetch(`${process.env["REACT_APP_REACT_SERVER_URL"]}/api/event/events/${data}`,{
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    })
        .then((res) => res.status);
}