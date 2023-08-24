import React from "react";
import {Container, Stack, useMantineTheme} from "@mantine/core";
import SearchBox from "../components/common/SearchBox";
import WebEventList from "../components/display/web/WebEventList";
import {useMediaQuery} from "react-responsive";
import MobileEventList from "../components/display/mobile/MobileEventList";
import CategoryBtn from "../components/event/CategoryBtn";

function Events() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <Container>
            <Stack align={"center"} style={{margin: "2rem 0 4rem"}}>
                <SearchBox/>
                <CategoryBtn/>
            </Stack>
            {mobile ? <MobileEventList /> : <WebEventList />}
        </Container>
    );
}

export default Events;