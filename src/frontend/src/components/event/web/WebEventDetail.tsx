import React, {useCallback, useMemo} from "react";
import {useLoaderData, useLocation, useNavigate} from "react-router-dom";
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
    Title
} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {IEventDetail} from "../../../types/IEvent";
import {userState} from "../../../states/userState";
import customStyle from "../../../styles/customStyle";
import {CheckLogin} from "../../../util/CheckLogin";
import WebTicketInfo from "./WebTicketInfo";
import {useModal} from "../../../util/hook/useModal";
import {useFetch} from "../../../util/hook/useFetch";

function WebEventDetail() {
    const userStateValue = useRecoilValue(userState);
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {loginAlertModal} = useModal();
    const {deleteEventFetch} = useFetch();
    const isLoggedIn = CheckLogin();
    const {classes} = customStyle();

    const DATA = useLoaderData() as IEventDetail;

    const eventStartAt = useMemo(() => new Date(DATA.eventStartAt), [DATA.eventStartAt]);
    const eventEndtAt = useMemo(() => new Date(DATA.eventEndAt), [DATA.eventEndAt]);

    const onClickTicket = useCallback(() => {
        if (isLoggedIn) {
            navigate(`/events/ticket/${DATA.id}`, {state:{
                title: DATA.title,
                tickets: DATA.tickets,
                }});
        } else {
            loginAlertModal();
        }
    }, [isLoggedIn]);

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
                            <Button color={"red"}
                                    variant={"outline"}
                                    onClick={() => deleteEventFetch(DATA.id)}
                                    style={{height: "2.5rem"}}>
                                행사 취소
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

export default React.memo(WebEventDetail);