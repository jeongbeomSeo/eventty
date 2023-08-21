const GATEWAY = "";
const MY_HEADERS = new Headers({"Content-Type": "application/json"},);

export const getEvent = (eventId: string) => {
    return fetch(`${GATEWAY}/api/events/${eventId}`)
        .then((res) => res.json());
}

export const getEvents = () => {
    return fetch(`${GATEWAY}/api/events`)
        .then((res) => res.json());
}