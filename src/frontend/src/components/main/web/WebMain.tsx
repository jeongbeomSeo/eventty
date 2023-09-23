import React from "react";
import WebCarousel from "./WebCarousel";
import {Button, Container, Divider, Flex, Grid, Group, SimpleGrid, Stack, Text, Title} from "@mantine/core";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import WebMainEventList from "./WebMainEventList";
import {googleLogout} from "@react-oauth/google";
import {useLoaderData} from "react-router-dom";
import {IEvent, IEventMain} from "../../../types/IEvent";

function WebMain() {
    const DATA = useLoaderData() as IEventMain;

    return (
        <>
            <WebCarousel/>
            <Container>
                <Stack spacing={"8rem"} style={{margin: "5rem 0"}}>
                    <Stack>
                        <Title order={2}>인기 상승중인 행사</Title>
                        <WebMainEventList data={DATA.Top10Views}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>신규 행사</Title>
                        <WebMainEventList data={DATA.Top10CreatedAt}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>마감 임박</Title>
                        <WebMainEventList data={DATA.Top10ApplyEndAt}/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default WebMain;