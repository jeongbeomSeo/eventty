import React from "react";
import {Badge, Divider, Group, Paper, Stack, Text, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";
import {useLoaderData} from "react-router-dom";
import {IEventDetail, IEventTicket, IEventTicketDetail} from "../../../types/IEvent";

interface ITickets {
    tickets: IEventTicket[];
    onClick: () => void;
}

function WebTicketInfo({tickets, onClick}:ITickets) {
    const items = tickets.map(item => (
        <TicketBtn name={item.name} price={item.price} quantity={item.quantity} onClick={onClick}/>
    ));

    return (
        <Stack>
            <Title order={4}>티켓 선택</Title>
            {items}
        </Stack>
    );
}

export default WebTicketInfo;