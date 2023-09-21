import React, {useCallback, useMemo} from "react";
import {useLoaderData, useLocation, useNavigate, useRouteLoaderData} from "react-router-dom";
import {
    Avatar,
    Button,
    Container,
    Divider,
    Grid,
    Group,
    Image,
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
import {useFetch} from "../../../util/hook/useFetch";
import {Base64toSrc} from "../../../util/ConvertFile";

function WebEventDetail() {
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {loginAlertModal} = useModal();
    const {deleteEventFetch} = useFetch();
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
            <Grid gutter={"xl"}>
                <Grid.Col span={8}>
                    <Image src={Base64toSrc(DATA.image, DATA.originFileName)}
                           height={"400"}
                           withPlaceholder
                    />
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack justify={"space-between"} style={{height: "100%"}}>
                        <Stack>
                            <UnstyledButton onClick={() => navigate(`/events/category/${DATA.categoryName}`)}>
                                <Title order={5} color={"var(--primary)"}>{DATA.categoryName}</Title>
                            </UnstyledButton>
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
                        {userStateValue.isHost && (userStateValue.userId === DATA.userId) ?
                            <Button color={"red"}
                                    variant={"outline"}
                                    onClick={() => deleteEventFetch(DATA.id)}
                                    style={{height: "2.5rem"}}>
                                행사 취소
                            </Button> :
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

                        <WebTicketInfo tickets={DATA.tickets}/>
                    </Stack>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default React.memo(WebEventDetail);