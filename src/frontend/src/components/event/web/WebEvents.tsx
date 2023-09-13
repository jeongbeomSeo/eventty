import React, {useState} from "react";
import {Box, Button, Flex, Modal, Stack, Text} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileCategoryBtn from "../mobile/MobileCategoryBtn";
import WebEventList from "./WebEventList";
import customStyle from "../../../styles/customStyle";

function WebEvents() {
    const {classes} = customStyle();

    return (
        <>
            <Stack align={"center"} style={{margin: "2rem 0 3vh"}}>
                <Flex style={{width:"80%"}}>
                </Flex>
                <Box style={{width: "100%", height: "auto"}}>
                    <Flex gap={"1.5rem"} justify={"center"}>
                        <MobileCategoryBtn/>
                    </Flex>
                </Box>
            </Stack>
            <WebEventList/>
        </>
    );
}

export default WebEvents;