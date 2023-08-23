import React from "react";
import {Container, Stack, useMantineTheme} from "@mantine/core";
import SearchBox from "../components/common/SearchBox";
import WebList from "./events/WebList";
import {useMediaQuery} from "react-responsive";
import MobileList from "./events/MobileList";
import CategoryBtn from "../components/event/CategoryBtn";

function Events() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <Container>
            <Stack align={"center"} style={{margin: "2rem 0 4rem"}}>
                <SearchBox/>
                {/*<CategoryBtn/>*/}
            </Stack>
            {mobile ? <MobileList /> : <WebList />}
        </Container>
    );
}

export default Events;