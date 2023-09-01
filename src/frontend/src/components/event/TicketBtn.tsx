import React, {useState} from "react";
import {Badge, Divider, Group, Paper, Stack, Text} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import EventDetailModal from "./EventDetailModal";
import {CheckLogin} from "../../util/CheckLogin";
import {useLoaderData, useLocation, useNavigate} from "react-router-dom";

function TicketBtn(props: {title: string, content: string, price: number, left:number}) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {pathname} = useLocation();

    return (
        <>
            <Paper p={"md"} withBorder
                   onClick={() => navigate("ticket", {state: pathname})}
                   className={classes["ticket-select"]}>
                <Stack>
                    <Group position={"apart"}>
                        <Text fw={"1000"} fz={"xl"}>{props.price.toLocaleString("ko-kr")} 원</Text>
                        <Badge radius={"sm"} color={"red"} style={{padding: "0.7rem 0.5rem"}}>
                            {props.left.toLocaleString("ko-kr")}개 남음
                        </Badge>
                    </Group>
                    <Text fz={"sm"}>{props.title}</Text>
                    <Divider/>
                    <Text fz={"sm"}>{props.content}</Text>
                </Stack>
            </Paper>
        </>
    );
}

export default TicketBtn;