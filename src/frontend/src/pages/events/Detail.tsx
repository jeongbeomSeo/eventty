import React from "react";
import {useLoaderData} from "react-router-dom";
import {IEvent, IEventDetail} from "../../types/IEvent";
import {Button, Container, Divider, Grid, Image, Stack, Title} from "@mantine/core";
import customStyle from "../../styles/customStyle";

function Detail() {
    const DATA = useLoaderData() as IEventDetail;
    const EVENT_DETAIL = DATA.eventDetailResponseDTO;
    const EVENT_INFO = DATA.eventResponseDTO;

    const {classes} = customStyle();

    // console.log(EVENT_DETAIL);
    // console.log(EVENT_INFO);

    return (
        <Container>
            <Grid style={{marginTop: "5vh"}}>
                <Grid.Col span={8}>
                    <Image src={EVENT_INFO.image}
                           height={"400"}
                           withPlaceholder
                    />
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Stack>
                        <Title>{EVENT_INFO.title}</Title>
                        <div>{EVENT_INFO.location}</div>
                        <Button className={classes["btn-primary"]} style={{height:"2.5rem"}}>예약하기</Button>
                    </Stack>
                </Grid.Col>
            </Grid>
            <Divider my={"3rem"}/>
            <Grid>
                <Grid.Col span={8}>
                    {EVENT_DETAIL.content}
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <div>{EVENT_INFO.hostId}</div>
                    <div>Ticket Information</div>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default Detail;