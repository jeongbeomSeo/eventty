import React from "react";
import {Box, Flex} from "@mantine/core";
import {useLocation} from "react-router-dom";
import EventDetailNavBar from "./EventDetailNavBar";
import MobileNavBar from "./MobileNavBar";

function MobileBottomSection() {
    const {pathname} = useLocation();

    return (
        <Box
            style={{
                zIndex: 99,
                position: "fixed",
                background: "white",
                height: "8vh",
                width: "100%",
                bottom: "0",
                boxShadow: "2px 0 6px rgba(0, 0, 0, 0.1)",
            }}>
            <Flex style={{height: "100%"}} align={"center"}>
                {pathname.includes("/event/") ? <EventDetailNavBar/> : <MobileNavBar/>}
            </Flex>
        </Box>
    );
}

export default MobileBottomSection;