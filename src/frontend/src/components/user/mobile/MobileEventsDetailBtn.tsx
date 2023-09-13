import React from "react";
import {AspectRatio, Badge, Box, Button, Grid, Group, Image, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {Link, useLocation} from "react-router-dom";

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

function MobileEventsDetailBtn(props: IEventState) {
    const {classes} = customStyle();
    const {pathname} = useLocation();

    return (
        <UnstyledButton
            component={Link}
            to={`/events/${props.id}`}
            state={pathname}
            style={{border: "1px solid #cdcdcd", padding: "1.2rem", borderRadius: "0.3rem"}}>
            <Stack style={{opacity: props.state === 2 ? "0.3" : "",}}>
                <Group noWrap>
                    <StateBadge state={props.state}/>
                    <Text>{props.date.toLocaleDateString()} 까지</Text>
                </Group>
                <Grid>
                    <Grid.Col span={4}>
                        <AspectRatio ratio={4 / 3}>
                            <Image src={""}
                                   withPlaceholder/>
                        </AspectRatio>
                    </Grid.Col>
                    <Grid.Col span={"auto"}>
                        <Title order={4}>{props.title}</Title>
                    </Grid.Col>
                </Grid>
                {props.state !== 2 &&
                    <Button component={Link}
                            to={`/applices/${props.id}`}
                            className={classes["btn-primary"]}>
                        신청내역
                    </Button>}
            </Stack>
        </UnstyledButton>
    )
        ;
}

export default MobileEventsDetailBtn;