import React from "react";
import {Box, Container, Flex, Stack} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileEventList from "./MobileEventList";
import customStyle from "../../../styles/customStyle";
import MobileCategoryBtn from "./MobileCategoryBtn";
import {Outlet} from "react-router-dom";

function MobileEvents() {
    const {classes} = customStyle();

    return (
        <>
            <Stack align={"center"} style={{margin: "2rem 0 3vh"}}>
                <Box style={{width: "100%", height: "auto"}}>
                    <Flex gap={"5vw"} className={classes["category-scroll"]}>
                        <MobileCategoryBtn/>
                    </Flex>
                </Box>
            </Stack>
            <Outlet/>
        </>
    );
}

export default MobileEvents;