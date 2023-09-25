import React from "react";
import {Badge, Divider, Group, Paper, Stack, Text} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useNavigate} from "react-router-dom";

interface ITicket {
    id: number;
    name: string;
    price: number;
    quantity: number;
    applied: number;
}

function TicketBtn({id, name, price, quantity, applied}: ITicket) {
    const {classes} = customStyle();
    const navigate = useNavigate();

    const handleOnClick = (id:number) => {
        navigate(`booking?item=${id}`);
    }

    return (
        <>
            <Paper p={"md"} withBorder
                   onClick={() => handleOnClick(id)}
                   className={classes["ticket-select"]}>
                <Stack>
                    <Group position={"apart"}>
                        <Text fw={"1000"} fz={"xl"}>
                            {price > 0 ? `${price.toLocaleString("ko-kr")} 원` : "무료"}
                        </Text>
                        <Badge radius={"sm"} color={"red"} style={{padding: "0.7rem 0.5rem"}}>
                            {(quantity - applied).toLocaleString("ko-kr")}개 남음
                        </Badge>
                    </Group>
                    <Text fz={"sm"}>{name}</Text>
                </Stack>
            </Paper>
        </>
    );
}

export default TicketBtn;