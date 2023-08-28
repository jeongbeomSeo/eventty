import React from "react";
import {Box, Button, Container, Flex, Group, Stack, useMantineTheme} from "@mantine/core";
import SearchBox from "../components/common/SearchBox";
import WebEventList from "../components/event/web/WebEventList";
import {useMediaQuery} from "react-responsive";
import MobileEventList from "../components/event/mobile/MobileEventList";
import CategoryBtn from "../components/event/CategoryBtn";
import MobileCategoryBtn from "../components/event/mobile/MobileCategoryBtn";
import customStyle from "../styles/customStyle";

function Events() {
    const {classes} = customStyle();
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <Container>
            <Stack align={"center"} style={{margin: "2rem 0 3vh"}}>
                <SearchBox/>
                <Box style={{width: "100%", height: "auto"}}>
                    <Flex gap={"5vw"} className={classes["category-scroll"]}>
                        <MobileCategoryBtn/>
                    </Flex>
                </Box>
            </Stack>
            {mobile ? <MobileEventList/> : <WebEventList/>}
        </Container>
    );
}

export default Events;