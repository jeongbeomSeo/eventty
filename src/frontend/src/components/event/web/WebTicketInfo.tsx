import React from "react";
import {Badge, Divider, Group, Paper, Stack, Text, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";
import {useLoaderData, useNavigate} from "react-router-dom";
import {IEventDetail, IEventTicket, IEventTicketDetail} from "../../../types/IEvent";

interface ITickets {
    tickets: IEventTicketDetail[];
}

function WebTicketInfo({tickets}:ITickets) {
    const navigate = useNavigate();
    
    const items = tickets.map((item) => (
        <TicketBtn key={item.id}
                   name={item.name}
                   price={item.price}
                   quantity={item.quantity}
                   onClick={() => navigate(`booking?item=${item.id}`)}/>
    ));

    return (
        <Stack>
            <Title order={4}>티켓 선택</Title>
            {items}
        </Stack>
    );
}

export default WebTicketInfo;