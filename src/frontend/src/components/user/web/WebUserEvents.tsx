import React from "react";
import {Button, Divider, Group, Stack, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import EventsDetailBtn from "../EventsDetailBtn";

function WebUserEvents() {
    const {classes} = customStyle();
    return (
        <>
            <Stack>
                <Title order={3}>주최 내역</Title>
                <Divider/>
                <Group spacing={"0.5rem"}>
                    <Button compact radius={"5rem"} className={classes["btn-primary"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                </Group>
                <EventsDetailBtn id={"0"} title={"[행사 제목]"} state={0} date={new Date()}/>
                <EventsDetailBtn id={"0"} title={"[행사 제목]"} state={1} date={new Date()}/>
                <EventsDetailBtn id={"0"} title={"[행사 제목]"} state={2} date={new Date()}/>
            </Stack>
        </>
    );
}

export default WebUserEvents;