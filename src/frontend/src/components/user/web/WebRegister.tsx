import React from "react";
import {Button, Divider, Group, Stack, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import WebEventsDetailBtn from "./WebEventsDetailBtn";
import {useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";

function WebRegister() {
    const DATA = useLoaderData() as IEvent[];

    const items = DATA.map(item => (
        <WebEventsDetailBtn data={item}/>
    ));

    return (
        <>
            <Stack>
                <Title order={3}>주최 내역</Title>
                <Divider/>
                {items}
            </Stack>
        </>
    );
}

export default WebRegister;