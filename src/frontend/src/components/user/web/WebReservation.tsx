import React from "react";
import {Divider, Stack, Title} from "@mantine/core";
import WebEventsDetailBtn from "./WebEventsDetailBtn";
import customStyle from "../../../styles/customStyle";

function WebReservation() {
    const {classes} = customStyle();

    return (
        <>
            <Stack>
                <Title order={3}>예약 내역</Title>
                <Divider/>

                <WebEventsDetailBtn id={"1"} title={"[행사 제목]"} state={"open"} date={new Date()}/>
                <WebEventsDetailBtn id={"2"} title={"[행사 제목]"} state={"close"} date={new Date()}/>
                <WebEventsDetailBtn id={"3"} title={"[행사 제목]"} state={"cancel"} date={new Date()}/>
            </Stack>
        </>
    );
}

export default WebReservation;