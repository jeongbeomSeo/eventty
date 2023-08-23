import React, {useState} from "react";
import {Box, Button, Container, Flex, Group, SimpleGrid, Stack, Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {IconHome, IconMenu2, IconTicket, IconUser} from "@tabler/icons-react";
import SideNavigator from "./SideNavigator";
import customStyle from "../../styles/customStyle";
import {useRecoilState, } from "recoil";
import {navBarState} from "../../states/navBarState";

function MobileNavBar() {
    const {classes} = customStyle();

    const MENU_LIST = [
        {value: "행사", link: "/events", icon: <IconTicket size={"2rem"}/>},
        {value: "홈", link: "/", icon: <IconHome size={"2rem"}/>},
        {value: "버튼", link: "/error", icon: <IconMenu2 size={"2rem"}/>},
        {value: "마이이벤티", link: "/user", icon: <IconUser size={"2rem"}/>},
    ];

    const items = MENU_LIST.map((item) => (
        <UnstyledButton component={Link}
                        to={item.link}
                        className={classes["mobile-nav-link"]}
        >
            <Stack align={"center"} spacing={"0"}>
                {item.icon}
                <Text fz={"sm"}>{item.value}</Text>
            </Stack>
        </UnstyledButton>
    ));

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
            <Flex style={{height: "100%"}}>
                <Group position={"apart"} style={{width: "100%", padding: "0 2rem"}}>
                    <SideNavigator/>
                    {items}
                </Group>
            </Flex>
        </Box>
    );
}

export default MobileNavBar;