import React, {useState} from "react";
import {Box, Button, Flex, Group, Modal, Stack, Text, Title} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileCategoryBtn from "../mobile/MobileCategoryBtn";
import WebEventList from "./WebEventList";
import customStyle from "../../../styles/customStyle";
import WebCategoryBtn from "./WebCategoryBtn";
import {Outlet, useParams} from "react-router-dom";

function WebEvents() {
    const {classes} = customStyle();
    const {category} = useParams();

    return (
        <>
            {category && <Title>{category}</Title>}
            <Stack align={"center"} style={{margin: "2rem 0 3vh"}}>
                <Flex style={{width:"80%"}}>
                </Flex>
                <Box style={{width: "100%", height: "auto"}}>
                    <Group position={"apart"}>
                        <WebCategoryBtn/>
                    </Group>
                </Box>
            </Stack>
            <Outlet/>
        </>
    );
}

export default WebEvents;