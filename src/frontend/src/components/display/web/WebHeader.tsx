import {Container, Group, Header, Menu, UnstyledButton,} from "@mantine/core";
import {Link} from "react-router-dom";
import LoginBtn from "./LoginBtn";
import Logo from "../../common/Logo";
import customStyle from "../../../styles/customStyle";
import React from "react";

const HEADER_HEIGHT = "65px";

function WebHeader() {
    const {classes} = customStyle();

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
                        <Menu trigger={"hover"}>
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
                <LoginBtn/>
            </Container>
        </Header>
    )
}

export default WebHeader;