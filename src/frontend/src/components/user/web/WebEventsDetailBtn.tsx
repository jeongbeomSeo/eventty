import React from "react";
import {Badge, Button, Group, Image, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {useNavigate} from "react-router-dom";
import {CheckHost} from "../../../util/CheckHost";

interface IEventState {
    id: string;
    title: string;
    state: string;
    date: Date;
}

const eventState = {
    user: {
        open:{
            label: "게시중",
            color: "violet",
            variant: "filled",
        },
        close:{
            label: "예약 종료",
            color: "violet",
            variant: "outline",
        },
        cancel:{
            label: "행사 취소",
            color: "gray",
            variant: "outline",
        },
    },
    host: {
        open:{
            label: "예약 완료",
            color: "violet",
            variant: "filled",
        },
        close:{
            label: "행사 종료",
            color: "violet",
            variant: "outline",
        },
        cancel:{
            label: "예약 취소",
            color: "gray",
            variant: "outline",
        },
    },
}

function WebEventsDetailBtn(props: IEventState) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const isHost = CheckHost();

    return (
        <UnstyledButton
            onClick={() => navigate(`/event/${props.id}`)}
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Group noWrap position={"apart"} style={{opacity: props.state === "cancel" ? "0.3" : "",}}>
                <Group>
                    <Image src={""}
                           width={100}
                           height={80}
                           radius={"md"}
                           withPlaceholder/>
                    <Stack>
                        <Title order={4} lineClamp={1}>{props.title}</Title>
                        <Group noWrap>
                            <Badge size={"lg"}
                                   radius={"0.3rem"}
                                   color={"violet"}
                                   variant={"filled"}
                                   style={{width: "6rem"}}>
                            </Badge>
                            <Text>{props.date.toLocaleDateString()} 까지</Text>
                        </Group>
                    </Stack>
                </Group>
                {props.state !== "cancel" &&
                    <Button onClick={(e) => {
                        e.stopPropagation();
                        navigate(`/applices/${props.id}`)
                    }}
                            className={classes["btn-primary"]}>
                        신청내역
                    </Button>}
            </Group>
        </UnstyledButton>
    );
}

export default WebEventsDetailBtn;