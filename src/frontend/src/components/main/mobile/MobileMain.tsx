import React from "react";
import MobileCarousel from "./MobileCarousel";
import {Box, Container, Flex, Stack, Title} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";
import MobileMainEventList from "./MobileMainEventList";
import WebMainEventList from "../web/WebMainEventList";
import {useLoaderData} from "react-router-dom";
import {IEventMain} from "../../../types/IEvent";

function MobileMain() {
    const {classes} = customStyle();
    const DATA = useLoaderData() as IEventMain;

    return (
        <>
            <MobileCarousel/>
            <Container>
                <Box style={{width:"100%", margin: "2.5rem 0"}}>
                    <Flex gap={"7vw"} className={classes["category-scroll"]}>
                        <MobileCategoryBtn/>
                    </Flex>
                </Box>

                <Stack spacing={"5rem"} style={{marginBottom: "5rem"}}>
                    <Stack>
                        <Title order={2}>인기 상승중인 행사</Title>
                        <MobileMainEventList data={DATA.Top10Views}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>신규 행사</Title>
                        <MobileMainEventList data={DATA.Top10CreatedAt}/>
                    </Stack>

                    <Stack>
                        <Title order={2}>마감 임박</Title>
                        <MobileMainEventList data={DATA.Top10ApplyEndAt}/>
                    </Stack>
                </Stack>
            </Container>
        </>
    );
}

export default MobileMain;