import React from "react";
import {Badge, Divider, Group, Paper, Stack, Text} from "@mantine/core";
import customStyle from "../../styles/customStyle";

interface ITicket {
    title: string;
    content: string;
    price: number;
    left: number;
    onClick: () => void;
}

function TicketBtn({title, content, price, left, onClick}: ITicket) {
    const {classes} = customStyle();

    return (
        <>
            <Paper p={"md"} withBorder
                   onClick={onClick}
                   className={classes["ticket-select"]}>
                <Stack>
                    <Group position={"apart"}>
                        <Text fw={"1000"} fz={"xl"}>{price.toLocaleString("ko-kr")} 원</Text>
                        <Badge radius={"sm"} color={"red"} style={{padding: "0.7rem 0.5rem"}}>
                            {left.toLocaleString("ko-kr")}개 남음
                        </Badge>
                    </Group>
                    <Text fz={"sm"}>{title}</Text>
                    <Divider/>
                    <Text fz={"sm"}>{content}</Text>
                </Stack>
            </Paper>
        </>
    );
}

export default TicketBtn;