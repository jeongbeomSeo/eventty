import React from "react";
import {Avatar, Container, Divider, Group, Image, Paper, Stack, Title} from "@mantine/core";
import {IEventDetail} from "../../../types/IEvent";
import {useLoaderData} from "react-router-dom";

function HostInfo({hostId}: {hostId: number}) {
    return(
        <Paper p={"md"} withBorder>
            <Group noWrap>
                <Avatar radius={"xl"}/>
                <div>
                    {hostId}
                </div>
            </Group>
            <Group>
                content
            </Group>
        </Paper>
    )
}

function MobileEventDetail() {
    const DATA = useLoaderData() as IEventDetail;
    const EVENT_DETAIL = DATA.eventDetailResponseDTO;
    const EVENT_INFO = DATA.eventResponseDTO;

    return (
        <>
            <Image src={EVENT_INFO.image}
                   height={"30vh"}
                   withPlaceholder/>
            <Container>
                <Stack style={{marginTop: "5vh"}}>
                    <Title>{EVENT_INFO.title}</Title>
                    <div>{EVENT_INFO.location}</div>
                    <Divider/>
                    <HostInfo hostId={EVENT_INFO.hostId}/>
                    <div>{EVENT_DETAIL.content}</div>
                </Stack>
            </Container>
        </>
    );
}

export default MobileEventDetail;