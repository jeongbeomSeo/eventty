import React from 'react';
import {Container} from "@mantine/core";
import {CheckXsSize} from "../util/CheckMediaQuery";
import MobileBookingTicket from "../components/ticket/mobile/MobileBookingTicket";
import WebBookingTicket from "../components/ticket/web/WebBookingTicket";

function Ticket() {
    const isXsSize = CheckXsSize();

    return (
        <Container>
            {isXsSize ? <MobileBookingTicket/> : <WebBookingTicket/>}
        </Container>
    )
}

export default Ticket;