import {ActionIcon, Box, Button, Container, Group, Header, Menu, TextInput, UnstyledButton,} from "@mantine/core";
import {Link} from "react-router-dom";
import WebLoginBtn from "./WebLoginBtn";
import Logo from "../../../common/Logo";
import customStyle from "../../../../styles/customStyle";
import React from "react";
import {useRecoilValue} from "recoil";
import {loginState} from "../../../../states/loginState";
import WebUserInfoBtn from "./WebUserInfoBtn";
import {IconSearch} from "@tabler/icons-react";
import {useModal} from "../../../../util/hook/useModal";

const HEADER_HEIGHT = "65px";

function WebHeader() {
    const isLoggedIn = useRecoilValue(loginState);
    const {classes} = customStyle();
    const {searchModal} = useModal();

    return (
        <Header height={HEADER_HEIGHT} className={classes["header"]}>
            <Container style={{height: "100%", display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <Group spacing={"lg"} align={"center"}>
                    <Link to={"/"}>
                        <Logo fill={"var(--primary)"} height={"2rem"}/>
                    </Link>
                    <Group>
                        <UnstyledButton component={Link} to={"/events"} className={classes["web-nav-link"]}>
                            행사
                        </UnstyledButton>
                        <Menu trigger={"hover"} shadow={"sm"}>
                            <Menu.Target>
                                <UnstyledButton className={classes["web-nav-link"]}>더보기</UnstyledButton>
                            </Menu.Target>
                            <Menu.Dropdown>
                                <Menu.Item component={Link} to={""}>SUB1</Menu.Item>
                                <Menu.Item component={Link} to={""}>SUB2</Menu.Item>
                                <Menu.Item component={Link} to={""}>SUB3</Menu.Item>
                                <Menu.Item component={Link} to={""}>SUB4</Menu.Item>
                            </Menu.Dropdown>
                        </Menu>
                    </Group>
                </Group>
                <Group>
                    <Button onClick={() => searchModal()}
                            style={{padding: "0 0.4rem"}}
                            className={classes["btn-gray-outline"]}>
                        <IconSearch/>
                    </Button>
                    {isLoggedIn ? <WebUserInfoBtn/> : <WebLoginBtn/>}
                </Group>
            </Container>
        </Header>
    )
}

export default WebHeader;