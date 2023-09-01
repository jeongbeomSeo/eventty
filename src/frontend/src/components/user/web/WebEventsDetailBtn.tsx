import React from "react";
import {Badge, Button, Group, Image, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {Link} from "react-router-dom";

enum State {
    open,
    close,
    cancel,
}

interface IEventState {
    id: string;
    title: string;
    state: State;
    date: Date;
}

function StateBadge({state}: { state: number }) {
    switch (state) {
        case 0:
            return (
                <Badge size={"lg"}
                       radius={"0.3rem"}
                       color={"violet"}
                       variant={"filled"}
                       style={{width: "6rem"}}>
                    게시중
                </Badge>
            )
        case 1:
            return (
                <Badge size={"lg"}
                       radius={"0.3rem"}
                       color={"violet"}
                       variant={"outline"}
                       style={{width: "6rem"}}>
                    예약 종료
                </Badge>
            )
        case 2:
            return (
                <Badge size={"lg"}
                       radius={"0.3rem"}
                       color={"gray"}
                       variant={"outline"}
                       style={{width: "6rem"}}>
                    행사 취소
                </Badge>
            )
    }
    return (<></>);
}

function WebEventsDetailBtn(props: IEventState) {
    const {classes} = customStyle();

    return (
        <UnstyledButton style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Group noWrap position={"apart"} style={{opacity: props.state === 2 ? "0.3" : "",}}>
                <Group>
                    <Image src={""}
                           width={100}
                           height={80}
                           radius={"md"}
                           withPlaceholder/>
                    <Stack>
                        <Title order={4}>{props.title}</Title>
                        <Group noWrap>
                            <StateBadge state={props.state}/>
                            <Text>{props.date.toLocaleDateString()} 까지</Text>
                        </Group>
                    </Stack>
                </Group>
                {props.state !== 2 &&
                    <Button component={Link}
                            to={`/applices/${props.id}`}
                        className={classes["btn-primary"]}>
                        신청내역
                    </Button>}
            </Group>
        </UnstyledButton>
    );
}

export default WebEventsDetailBtn;