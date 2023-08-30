import React from "react";
import {Badge, Divider, Group, Paper, Stack, Text, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";

function WebTicketInfo() {
    return (
        <Stack>
            <Title order={4}>티켓 선택</Title>
            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
        </Stack>
    );
}

export default WebTicketInfo;