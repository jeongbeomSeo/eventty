import React, {useEffect, useState} from 'react';
import {
    Button,
    Card,
    CardSection, Container,
    Divider,
    Flex,
    Grid,
    Group,
    Input, NumberInput,
    Paper, SimpleGrid,
    Stack,
    Stepper,
    Text, TextInput,
    Title, UnstyledButton
} from "@mantine/core";
import {useLoaderData, useLocation, useParams, useRouteLoaderData, useSearchParams} from "react-router-dom";
import {IEventBooking, IEventDetail, IEventTicketDetail} from "../../../types/IEvent";
import {getEvent} from "../../../service/event/fetchEvent";
import customStyle from "../../../styles/customStyle";
import {IconCalendar, IconDeviceMobile, IconPhone, IconTicket, IconUsers} from "@tabler/icons-react";
import PhoneNumberInput from "../../common/PhoneNumberInput";
import {IUser} from "../../../types/IUser";
import {getProfile} from "../../../service/user/fetchUser";
import {useForm} from "react-hook-form";

function WebBookingTicket() {
    const {classes} = customStyle();
    const EVENT_DATA = useRouteLoaderData("event") as IEventDetail;
    const [PROFILE_DATA, setPROFILE_DATA] = useState<IUser>();
    const [searchParams, setSearchParams] = useSearchParams();
    const [curTicket, setCurTicket] = useState<IEventTicketDetail>(EVENT_DATA.tickets[0]);
    const [totalPrice, setTotalPrice] = useState(curTicket.price);
    const eventStartAt = new Date(EVENT_DATA.eventStartAt);
    const eventEndAt = new Date(EVENT_DATA.eventEndAt);

    const {register, handleSubmit} = useForm<IEventBooking>();

    const ticketItems = EVENT_DATA.tickets.map((item) => (
        <UnstyledButton key={item.id}>
            <Stack>
                <Text>{item.name}</Text>
                <Text>{item.price.toLocaleString("ko-kr")}원</Text>
            </Stack>
        </UnstyledButton>
    ));

    useEffect(() => {
        const ticketId = Number(searchParams.get("item"));
        const item = EVENT_DATA.tickets.find(value => value.id === ticketId)!;

        if (typeof item !== "undefined") {
            setCurTicket(item);
            setTotalPrice(item.price);
        }
    }, [searchParams]);

    useEffect(() => {
        getProfile()
            .then(res => setPROFILE_DATA(res));
    }, []);

    return (
        <Container>
            <Grid>
                <Grid.Col span={8}>
                    <Stack style={{height:"100%"}}>
                        <Paper p={"1rem"}>
                            <Group noWrap>
                                <Title order={4}>{EVENT_DATA.title}</Title>
                            </Group>
                        </Paper>

                        <Paper p={"1rem"}>
                            <Stack spacing={"2rem"}>
                                <Group align={"flex-start"} noWrap>
                                    <IconTicket color={"var(--primary)"}/>
                                    <Stack style={{width: "100%"}} align={"flex-start"}>
                                        <Title order={4}>티켓</Title>
                                        <SimpleGrid cols={3}>
                                            {ticketItems}
                                        </SimpleGrid>
                                    </Stack>
                                </Group>

                                <Group align={"flex-start"} noWrap>
                                    <IconUsers color={"var(--primary)"}/>
                                    <Stack style={{width: "100%"}} align={"flex-start"}>
                                        <Title order={4}>인원</Title>
                                        <NumberInput defaultValue={1}
                                                     min={1}
                                                     max={999}
                                                     className={classes["input"]}
                                                     style={{width:"50%"}}/>
                                    </Stack>
                                </Group>
                            </Stack>
                        </Paper>

                        <Paper p={"1rem"} style={{height: "100%"}}>
                            <Group align={"flex-start"} noWrap>
                                <IconDeviceMobile color={"var(--primary)"}/>
                                <Stack style={{width: "100%"}} align={"flex-start"}>
                                    <Title order={4}>연락처</Title>
                                    <TextInput withAsterisk
                                               defaultValue={PROFILE_DATA?.phone}
                                               description={"휴대폰 번호"}
                                               className={classes["input"]}
                                               style={{width: "50%"}}/>
                                </Stack>
                            </Group>
                        </Paper>
                    </Stack>
                </Grid.Col>

                <Grid.Col span={4}>
                    <Paper p={"1rem"} style={{height: "100%"}}>
                        <Stack>
                            <Title order={4}>예약 정보</Title>
                            <Divider my={"0.5rem"}/>

                            <Group position={"apart"}>
                                <Text>{curTicket.name}</Text>
                                <Text>{curTicket.price.toLocaleString("ko-kr")} x 1</Text>
                            </Group>
                            <Divider my={"0.5rem"}/>

                            <Stack spacing={"1.5rem"}>
                                <Stack spacing={"0.5rem"}>
                                    <Title order={6}>행사</Title>
                                    <Title order={5}>{EVENT_DATA.title}</Title>
                                </Stack>

                                <Stack spacing={"0.5rem"}>
                                    <Title order={6}>예약 일정</Title>
                                    <Group align={"flex-start"} noWrap>
                                        <IconCalendar color={"var(--primary)"}/>
                                        <Flex direction={"column"}>
                                            <Title order={5}>
                                                {`${eventStartAt.getFullYear()}년 
                                            ${eventStartAt.getMonth() + 1}월 
                                            ${eventStartAt.getDate()}일`}
                                            </Title>
                                            <Title order={5}>
                                                {`${eventStartAt.getHours()}:${eventStartAt.getMinutes() < 10 && "0"}${eventStartAt.getMinutes()} -
                                                ${eventEndAt.getHours()}:${eventEndAt.getMinutes() < 10 && "0"}${eventEndAt.getMinutes()}`}
                                            </Title>
                                        </Flex>
                                    </Group>
                                </Stack>
                            </Stack>
                            <Divider my={"0.5rem"}/>

                            <Group position={"apart"}>
                                <Title order={5}>총 금액</Title>
                                <Title order={4}>{totalPrice.toLocaleString("ko-kr")} 원</Title>
                            </Group>
                            <Button className={classes["btn-primary"]}>결제하기</Button>
                        </Stack>
                    </Paper>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebBookingTicket;