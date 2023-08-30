import React, {useState} from "react";
import {
    Avatar,
    Burger, Button,
    Container, Divider,
    Drawer,
    Flex,
    Group,
    Navbar,
    Stack,
    Text,
    Title,
    UnstyledButton
} from "@mantine/core";
import {Link, Navigate, useLocation} from "react-router-dom";
import {IconHome, IconLogout, IconMenu2, IconPlus, IconReceipt, IconSettings, IconUser} from "@tabler/icons-react";
import customStyle from "../../../styles/customStyle";
import {useRecoilState} from "recoil";
import {menuDrawerState} from "../../../states/menuDrawerState";
import {CheckLogin} from "../../../util/CheckLogin";

function MobileMenuDrawer() {
    const {classes} = customStyle();
    const {pathname} = useLocation();
    const isLoggedIn = CheckLogin();
    const [opened, setOpened] = useRecoilState(menuDrawerState);

    const MENU_LIST = [
        {value: "홈", link:"/", icon:<IconHome/>},
        {value: "주최하기", link: "/write/event", icon: <IconPlus/>},
        {value: "마이페이지", link:"/users/profile", icon:<IconUser/>},
        {value: "예약 내역", link:"/users/reservations", icon:<IconReceipt/>},
        {value: "설정", link:"", icon:<IconSettings/>},
    ];

    const items = MENU_LIST.map(item => (
        <UnstyledButton component={Link} to={item.link} style={{padding:"0.2rem 0"}}>
            <Group>
                {item.icon}
                <Text>{item.value}</Text>
            </Group>
        </UnstyledButton>
    ));

    const handleMenuDrawer = () => {
        setOpened((prev) => !prev);
    }

    return (
        <>
            <Drawer.Root opened={opened}
                         onClose={handleMenuDrawer}
                         title={"사이드 메뉴"}
                         position={"right"}
                         size={"70%"}
                         transitionProps={{duration: 400}}
                         style={{zIndex: 1}}
            >
                <Drawer.Overlay opacity={0.5} blur={2}/>
                <Drawer.Content>
                    <Container>
                        <Drawer.Header>
                            <Drawer.CloseButton/>
                        </Drawer.Header>
                        <Drawer.Body>
                            <Stack>
                                <Group>
                                    <Avatar size={"4rem"} radius={"4rem"}/>
                                    <Text>{!isLoggedIn && "로그인"}</Text>
                                </Group>
                                <Divider/>

                                {items}

                                <Divider/>
                                <Button component={Link}
                                        to={isLoggedIn ? "/logout" : "/login"}
                                        state={pathname}
                                        className={classes["btn-gray-outline"]}>
                                    {isLoggedIn ? "로그아웃" : "로그인"}
                                </Button>
                            </Stack>
                        </Drawer.Body>
                    </Container>
                </Drawer.Content>
            </Drawer.Root>
        </>
    );
}

export default MobileMenuDrawer;