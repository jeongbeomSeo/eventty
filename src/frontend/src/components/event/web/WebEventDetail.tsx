import React, {useState} from "react";
import {useLoaderData, useLocation, useNavigate} from "react-router-dom";
import {
    Avatar,
    Badge,
    Button,
    Container,
    Divider, Flex,
    Grid,
    Group,
    Image, Modal,
    Paper,
    Stack,
    Text,
    Title
} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {IEventDetail} from "../../../types/IEvent";
import {userState} from "../../../states/userState";
import customStyle from "../../../styles/customStyle";
import {CheckLogin} from "../../../util/CheckLogin";
import WebTicketInfo from "./WebTicketInfo";
import TicketBtn from "../TicketBtn";
import {useModal} from "../../../util/hook/useModal";

function WebEventDetail() {
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {loginAlertModal} = useModal();
    const isLoggedIn = CheckLogin();
    const {classes} = customStyle();

    const DATA = useLoaderData() as IEventDetail;

    const eventStartAt = new Date(DATA.eventStartAt);
    const eventEndtAt = new Date(DATA.eventEndAt);

    const onClickTicket = () => {
        if (isLoggedIn) {
            navigate("ticket", {state: pathname});
        } else {
            loginAlertModal();
        }
    }

    return (
        <Container>
            <Grid style={{marginTop: "5vh"}} gutter={"xl"}>
                <Grid.Col span={8}>
                    <Image src={DATA.image}
                           height={"400"}
                           withPlaceholder
                    />
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack justify={"space-between"} style={{height: "100%"}}>
                        <Stack>
                            <Text fz={"1rem"} color={"var(--primary)"}>{DATA.categoryName}</Text>
                            <Title order={2}>{DATA.title}</Title>
                            <Title order={4}>
                                {`${eventStartAt.getMonth() + 1}월 ${eventStartAt.getDate()}일`}
                                {((eventStartAt.getMonth() === eventEndtAt.getMonth()) && (eventStartAt.getDate() === eventEndtAt.getDate())) ?
                                    ` ${eventStartAt.getHours()}시 ~ ${eventEndtAt.getHours()}시` :
                                    ` ~ ${eventEndtAt.getMonth() + 1}월 ${eventEndtAt.getDate()}일`
                                }
                            </Title>
                            <Text>{DATA.location}</Text>
                        </Stack>
                        {userStateValue.isHost ?
                            <Button className={`${classes["btn-primary"]} disable`}
                                    style={{height: "2.5rem"}}>
                                예약 불가
                            </Button> :
                            <Button className={classes["btn-primary"]}
                                    style={{height: "2.5rem"}}
                                    onClick={onClickTicket}>
                                예약
                            </Button>
                        }
                    </Stack>
                </Grid.Col>
            </Grid>

            <Divider my={"3rem"}/>

            <Grid gutter={"xl"}>
                <Grid.Col span={8}>
                    {DATA.content}
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack spacing={"3rem"}>
                        <Paper p={"md"} withBorder>
                            <Group noWrap>
                                <Avatar radius={"xl"}/>
                                <div>
                                    {DATA.userId}
                                </div>
                            </Group>
                            <Group>
                                content
                            </Group>
                        </Paper>

                        <WebTicketInfo tickets={DATA.tickets} onClick={onClickTicket}/>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebEventDetail;