export const getEvent = async (eventId: string) => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/events/${eventId}`)
        .then((res) => res.json())
        .then((res) => res.data);
}

export const getEvents = async () => {
    return await fetch(`${process.env["REACT_APP_GATEWAY_SERVER_URL"]}/api/events`)
        .then((res) => res.json())
        .then((res) => res.data);
}