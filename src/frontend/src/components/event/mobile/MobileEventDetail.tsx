import React, {useMemo} from "react";
import {Avatar, Container, Divider, Group, Image, Paper, Stack, Text, Title} from "@mantine/core";
import {IEvent, IEventDetail} from "../../../types/IEvent";
import {useLoaderData, useLocation, useNavigate} from "react-router-dom";
import EventDetailNavBar from "../../display/mobile/navbar/EventDetailNavBar";
import MobileTicketInfo from "./MobileTicketInfo";
import {useRecoilValue} from "recoil";
import {eventTicketDrawerState} from "../../../states/eventTicketDrawerState";
import {CheckLogin} from "../../../util/CheckLogin";
import {useModal} from "../../../util/hook/useModal";

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
    const eventTicketDrawerValue = useRecoilValue(eventTicketDrawerState);
    const DATA = useLoaderData() as IEventDetail;

    const eventStartAt = useMemo(() => new Date(DATA.eventStartAt), [DATA.eventStartAt]);
    const eventEndtAt = useMemo(() => new Date(DATA.eventEndAt), [DATA.eventEndAt]);

    return (
        <>
            <Image src={DATA.image}
                   height={"30vh"}
                   withPlaceholder/>
            <Container>
                <Stack style={{marginTop: "5vh"}}>
                    <Text fz={"1rem"} color={"var(--primary)"}>{DATA.categoryName}</Text>
                    <Title>{DATA.title}</Title>
                    <Title order={4}>{`${eventStartAt.getMonth()+1}월 ${eventStartAt.getDate()}일`}
                        {DATA.eventStartAt !== DATA.eventEndAt &&
                        `~ ${eventEndtAt.getMonth()+1}월 ${eventEndtAt.getDate()}일`}
                    </Title>
                    <Divider/>
                    <HostInfo hostId={DATA.userId}/>
                    <div>{DATA.content}</div>
                </Stack>
            </Container>
            <MobileTicketInfo open={eventTicketDrawerValue} tickets={DATA.tickets}/>
        </>
    );
}

export default React.memo(MobileEventDetail);