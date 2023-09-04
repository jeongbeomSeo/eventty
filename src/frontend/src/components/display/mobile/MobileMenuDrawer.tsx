import React, {useState} from "react";
import {Avatar, Burger, Container, Drawer, Flex, Group, Navbar, Stack, Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {IconMenu2} from "@tabler/icons-react";
import customStyle from "../../../styles/customStyle";
import {useRecoilState} from "recoil";
import {menuDrawerState} from "../../../states/menuDrawerState";

function MobileMenuDrawer() {
    const {classes} = customStyle();
    const [opened, setOpened] = useRecoilState(menuDrawerState);
    const handleMenuDrawer = () => {
        setOpened((prev) => !prev);
    }

    return (
        <>
            <Drawer.Root opened={opened}
                         onClose={handleMenuDrawer}
                         title={"사이드 메뉴"}
                         position={"left"}
                         size={"70%"}
                         transitionProps={{duration: 400}}
                         style={{zIndex:1}}
            >
                <Drawer.Overlay opacity={0.5} blur={2}/>
                <Drawer.Content>
                    <Container>
                        <Drawer.Header>
                            <Avatar radius={"xl"}/>
                            <div>타이틀</div>
                        </Drawer.Header>
                        <Drawer.Body>
                            몸통
                        </Drawer.Body>
                    </Container>
                </Drawer.Content>
            </Drawer.Root>
        </>
    );
}

export default MobileMenuDrawer;