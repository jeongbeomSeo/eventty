import React from "react";
import WebCarousel from "./WebCarousel";
import {Button, Container, Divider, Flex, Grid, Group, SimpleGrid, Stack, Title} from "@mantine/core";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import WebMainEventList from "./WebMainEventList";

function WebMain() {

    return (
        <>
            <WebCarousel/>
            <Container>
                <SimpleGrid cols={1} verticalSpacing={"7rem"} style={{margin: "5rem 0"}}>

                    <div>
                        <Title order={3}>인기 상승중인 행사</Title>
                        <WebMainEventList/>
                    </div>

                    <div>
                        <Title order={3}>신규 행사</Title>
                        <WebMainEventList/>
                    </div>

                    <div>
                        <Title order={3}>마감 임박</Title>
                        <WebMainEventList/>
                    </div>
                </SimpleGrid>
            </Container>
        </>
    );
}

export default WebMain;