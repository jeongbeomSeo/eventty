import React, {useEffect} from 'react';
import {Stack} from "@mantine/core";
import {useLoaderData, useParams} from "react-router-dom";
import {IEventDetail} from "../../../types/IEvent";
import {getEvent} from "../../../service/event/fetchEvent";

function WebBookingTicket() {
    const {eventId} = useParams();

    useEffect(() => {
        getEvent(eventId!)
            .then(res => console.log(res));
    }, []);

    return (
        <Stack style={{marginTop: "5vh"}}>
        </Stack>
    );
}

export default WebBookingTicket;