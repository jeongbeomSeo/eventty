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
    const EVENT_DETAIL = DATA.eventDetailResponseDTO;
    const EVENT_INFO = DATA.eventResponseDTO;

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
                    <Image src={EVENT_INFO.image}
                           height={"400"}
                           withPlaceholder
                    />
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack>
                        <Text fz={"xs"} color={"var(--primary)"}>{EVENT_INFO.category}</Text>
                        <Title order={2}>{EVENT_INFO.title}</Title>
                        <Text>{EVENT_INFO.location}</Text>
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
                    {EVENT_DETAIL.content}
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack spacing={"3rem"}>
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

                        <Stack>
                            <Title order={4}>티켓 선택</Title>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}
                                       onClick={onClickTicket}/>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}
                                       onClick={onClickTicket}/>
                        </Stack>
                        <WebTicketInfo/>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebEventDetail;