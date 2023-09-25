import React, {useCallback, useMemo} from "react";
import {useNavigate, useRouteLoaderData} from "react-router-dom";
import {
    Button,
    Container,
    Divider,
    Grid,
    Paper,
    Stack,
    Text,
    Title, UnstyledButton
} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {IEventDetail} from "../../../types/IEvent";
import {userState} from "../../../states/userState";
import customStyle from "../../../styles/customStyle";
import {CheckLogin} from "../../../util/CheckLogin";
import WebTicketInfo from "./WebTicketInfo";
import {useModal} from "../../../util/hook/useModal";
import DOMPurify from "dompurify";
import WebHostInfo from "./WebHostInfo";
import {CATEGORY_LIST} from "../../../util/const/categoryList";

function WebEventDetail() {
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {loginAlertModal, eventDeleteModal} = useModal();
    const isLoggedIn = CheckLogin();
    const {classes} = customStyle();

    const DATA = useRouteLoaderData("event") as IEventDetail;

    const eventStartAt = useMemo(() => new Date(DATA.eventStartAt), [DATA.eventStartAt]);
    const eventEndtAt = useMemo(() => new Date(DATA.eventEndAt), [DATA.eventEndAt]);

    const onClickTicket = useCallback(() => {
        if (isLoggedIn) {
            navigate(`/event/${DATA.id}/booking`);
        } else {
            loginAlertModal();
        }
    }, [isLoggedIn]);

    return (
        <Container style={{paddingTop: "5vh", paddingBottom: "5vh"}}>
            <Grid gutter={"xl"} align={"stretch"}>
                <Grid.Col span={8}>
                    <Paper
                        style={{
                            width: "100%",
                            height: 0,
                            paddingBottom: "75%",
                            backgroundImage: `url(${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${DATA.image})`,
                            backgroundRepeat: "no-repeat",
                            backgroundPosition: "center",
                            backgroundSize: "cover",
                        }}/>
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack justify={"space-between"} style={{height: "100%"}}>
                        <Stack>
                            <UnstyledButton onClick={() => navigate(`/events/category/${DATA.category}`)}>
                                <Title order={5} color={"var(--primary)"}>{CATEGORY_LIST[DATA.category]}</Title>
                            </UnstyledButton>
                            <Title order={2}>{DATA.title}</Title>
                            <Title order={4}>
                                {`${eventStartAt.getFullYear()}년 `}
                                {`${eventStartAt.getMonth()}월 `}
                                {`${eventStartAt.getDate()}일 `}
                                {!((eventStartAt.getFullYear() === eventEndtAt.getFullYear()) && (eventStartAt.getMonth() === eventEndtAt.getMonth()) && ((eventStartAt.getDate() === eventEndtAt.getDate())))
                                    && `${eventStartAt.getHours()}시
                                ${eventStartAt.getMinutes() !== 0 ? `${eventStartAt.getMinutes()}분` : ""}`}
                            </Title>
                            <Title order={4}>
                                {`${!((eventStartAt.getFullYear() === eventEndtAt.getFullYear()) && (eventStartAt.getMonth() === eventEndtAt.getMonth()) && ((eventStartAt.getDate() === eventEndtAt.getDate())))
                                    ? `~ ${eventEndtAt.getFullYear()}년  
                                    ${eventEndtAt.getMonth()}월 
                                    ${eventEndtAt.getDate()}일 
                                    ${eventEndtAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""} `
                                    : `${eventStartAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""}
                                    ~ ${eventEndtAt.getHours()}시 
                                    ${eventEndtAt.getMinutes() !== 0 ? `${eventEndtAt.getMinutes()}분` : ""}`}`}
                            </Title>
                            <Text color={"gray"}>{DATA.location}</Text>
                        </Stack>
                        {userStateValue.isHost && (userStateValue.userId === DATA.hostId) ?
                            <Stack>
                                <Button
                                    onClick={() => navigate(`/update/${DATA.id}`)}
                                    className={classes["btn-primary-outline"]}
                                >
                                    행사 수정
                                </Button>
                                <Button color={"red"}
                                        variant={"outline"}
                                        onClick={() => eventDeleteModal(DATA.id)}
                                        style={{height: "2.5rem"}}>
                                    행사 취소
                                </Button>
                            </Stack> :
                            userStateValue.isHost ?
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
                {/* XSS 방지 */}
                <Grid.Col span={8}
                          dangerouslySetInnerHTML={{__html: DOMPurify.sanitize(DATA.content)}}>
                </Grid.Col>

                <Grid.Col span={"auto"}>
                    <Stack spacing={"3rem"}>
                        <WebHostInfo hostName={DATA.hostName} hostPhone={DATA.hostPhone}/>
                        <WebTicketInfo tickets={DATA.tickets}/>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default React.memo(WebEventDetail);