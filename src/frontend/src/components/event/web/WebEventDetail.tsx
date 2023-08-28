import React, {useEffect, useRef, useState} from "react";
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
import AlertModal from "../../common/AlertModal";

function WebEventDetail() {
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {pathname} = useLocation();

    const isLoggedIn = CheckLogin();

    const DATA = useLoaderData() as IEventDetail;
    const EVENT_DETAIL = DATA.eventDetailResponseDTO;
    const EVENT_INFO = DATA.eventResponseDTO;

    const [modalOpened, setModalOpened] = useState(false);
    const {classes} = customStyle();

    const handleModalOpened = () => {
        setModalOpened(prev => !prev);
    }

    const onClickTicket = () => {
        if (isLoggedIn) {
            navigate("ticket", {state: pathname});
        } else {
            handleModalOpened();
        }
    }

    return (
        <>
            <Modal opened={modalOpened}
                   onClose={handleModalOpened}
                   withCloseButton={false}
                   centered
                   padding={"4rem"}
                   style={{textAlign: "center"}}>
                <Flex align={"center"} direction={"column"} gap={"1rem"} >
                    <Text align={"center"}>로그인 후 이용해주세요</Text>
                    <Button onClick={() => navigate("/login", {state: pathname})}
                            className={classes["btn-primary"]}>확인</Button>
                </Flex>
            </Modal>

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
                            <WebTicketInfo/>
                        </Stack>
                    </Grid.Col>
                </Grid>
            </Container>
        </>
    );
}

export default WebEventDetail;