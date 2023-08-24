import React, {useEffect, useRef, useState} from "react";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent, IEventDetail} from "../../types/IEvent";
import {
    Anchor,
    Avatar,
    Badge,
    Box,
    Button,
    Container,
    Divider,
    Grid,
    Group,
    Image, LoadingOverlay,
    Paper,
    Stack,
    Text,
    Title
} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useRecoilValue} from "recoil";
import {userState} from "../../states/userState";

function Detail() {
    const userStateValue = useRecoilValue(userState);

    const DATA = useLoaderData() as IEventDetail;
    const EVENT_DETAIL = DATA.eventDetailResponseDTO;
    const EVENT_INFO = DATA.eventResponseDTO;

    const {classes} = customStyle();
    const ticketSection = useRef<HTMLHeadingElement | null>(null);
    const handleScrollToTicket = () => {
        ticketSection.current?.scrollIntoView({behavior: "smooth", block: "start"})
    };

    // console.log(EVENT_DETAIL);
    // console.log(EVENT_INFO);

    return (
        <Container>
            <Grid style={{marginTop: "5vh"}} gutter={"xl"}>
                <Grid.Col span={8}>
                    <Image src={EVENT_INFO.image}
                           height={"400"}
                           withPlaceholder
                    />
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack>
                        <Title>{EVENT_INFO.title}</Title>
                        <div>{EVENT_INFO.location}</div>
                        {userStateValue.isHost ?
                            <Button className={`${classes["btn-primary"]} disable`}
                                    style={{height: "2.5rem"}}>
                                예약 불가
                            </Button> :
                            <Button className={classes["btn-primary"]}
                                    style={{height: "2.5rem"}}
                                    onClick={handleScrollToTicket}>
                                예약
                            </Button>
                        }
                    </Stack>
                </Grid.Col>
            </Grid>
            <Divider my={"3rem"}/>
            <Grid gutter={"xl"}>
                <Grid.Col span={8}>
                    {EVENT_DETAIL.content}
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack>
                        <Paper p={"md"} withBorder>
                            <Group noWrap>
                                <Avatar radius={"xl"}/>
                                <div>
                                    {EVENT_INFO.hostId}
                                </div>
                            </Group>
                            <Group>
                                content
                            </Group>
                        </Paper>
                        <div ref={ticketSection}>
                            <Title order={4}>티켓 선택</Title>
                            <Paper p={"md"} withBorder
                                   className={classes["ticket-select"]}
                            >
                                <Stack>
                                    <Group position={"apart"}>
                                        <Text fw={"1000"} fz={"xl"}>999,999,999 원</Text>
                                        <Badge radius={"sm"} color={"red"} style={{padding: "0.7rem 0.5rem"}}>00개
                                            남음</Badge>
                                    </Group>
                                    <Text fz={"sm"}>티켓 제목</Text>
                                    <Divider/>
                                    <Text>내용</Text>
                                </Stack>
                            </Paper>
                        </div>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default Detail;