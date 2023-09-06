// process.env["REACT_APP_SERVER_URL"]

export const getEvent = async (eventId: string) => {
    return await fetch(`/api/events/${eventId}`)
        .then((res) => res.json())
        .then((res) => res.data);
}

export const getEvents = async () => {
    return await fetch("http://localhost:8001/api/events")
        .then((res) => res.json())
        .then((res) => res.data);
}