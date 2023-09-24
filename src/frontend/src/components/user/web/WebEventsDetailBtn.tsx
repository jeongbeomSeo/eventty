import React from "react";
import {Badge, Button, Group, Image, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {useNavigate} from "react-router-dom";
import {CheckHost} from "../../../util/CheckHost";
import {IEvent} from "../../../types/IEvent";

const eventState = {
    host: {
        open: {
            label: "게시중",
            color: "indigo.7",
            variant: "light",
        },
        close: {
            label: "예약 종료",
            color: "indigo.7",
            variant: "outline",
        },
    },
    user: {
        open: {
            label: "예약 완료",
            color: "indigo.7",
            variant: "light",
        },
        close: {
            label: "행사 종료",
            color: "indigo.7",
            variant: "outline",
        },
    },
}

function WebEventsDetailBtn({data}: { data: IEvent }) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const role = CheckHost() ? "host" : "user";
    const state = data.isActive ? "open" : "close";
    const eventStartAt = new Date(data.eventStartAt);
    const eventEndAt = new Date(data.eventEndAt);

    return (
        <UnstyledButton
            onClick={() => navigate(`/event/${data.id}`)}
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Group noWrap position={"apart"} style={{opacity: state === "close" ? "0.3" : "",}}>
                <Group>
                    <Image src={`${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${data.image}`}
                           width={100}
                           height={80}
                           radius={"md"}
                           withPlaceholder/>
                    <Stack>
                        <Title order={4} lineClamp={1}>{data.title}</Title>
                        <Group noWrap>
                            <Badge size={"lg"}
                                   radius={"lg"}
                                   color={eventState[role][state]["color"]}
                                   variant={eventState[role][state]["variant"]}
                                   style={{width: "5rem"}}>
                                {eventState[role][state]["label"]}
                            </Badge>
                            <Text fz={"sm"}>
                                {`${eventStartAt.getFullYear()}년
                                ${eventStartAt.getMonth()}월
                                ${eventStartAt.getDate()}일`}
                                {` ~ ${eventEndAt.getFullYear()}년
                                ${eventEndAt.getMonth()}월
                                ${eventEndAt.getDate()}일`}
                            </Text>
                        </Group>
                    </Stack>
                </Group>
                <Button onClick={(e) => {
                    e.stopPropagation();
                    navigate(`/applices/${data.id}`)
                }}
                        className={classes["btn-primary"]}>
                    신청내역
                </Button>
            </Group>
        </UnstyledButton>
    );
}

export default WebEventsDetailBtn;