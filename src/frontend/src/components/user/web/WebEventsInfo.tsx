import React from "react";
import {Button, Divider, Group, Stack, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import WebEventsDetailBtn from "./WebEventsDetailBtn";

function WebEventsInfo() {
    const {classes} = customStyle();
    return (
        <>
            <Stack>
                <Title order={3}>주최 내역</Title>
                <Divider/>
                {/*<Group spacing={"0.5rem"}>
                    <Button compact radius={"5rem"} className={classes["btn-primary"]}>category</Button>
                    <Button compact radius={"5rem"} className={classes["btn-primary-outline"]}>category</Button>
                </Group>*/}
                <WebEventsDetailBtn id={"1"} title={"[행사 제목]"} state={0} date={new Date()}/>
                <WebEventsDetailBtn id={"2"} title={"[행사 제목]"} state={1} date={new Date()}/>
                <WebEventsDetailBtn id={"3"} title={"[행사 제목]"} state={2} date={new Date()}/>
            </Stack>
        </>
    );
}

export default WebEventsInfo;