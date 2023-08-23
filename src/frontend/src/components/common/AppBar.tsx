import {Container, Divider, Group, Header, useMantineTheme} from "@mantine/core";
import {Link} from "react-router-dom";
import LoginBtn from "./LoginBtn";
import Logo from "./Logo";
import customStyle from "../../styles/customStyle";
import SideNavigator from "./SideNavigator";
import {useMediaQuery} from "react-responsive";
import React from "react";

const HEADER_HEIGHT = "65px";
const NAV_LIST = [
    {name: "목록", link: "/events"},
    {name: "SUB2", link: ""},
    {name: "SUB3", link: ""},
]


function AppBar() {
    const theme = useMantineTheme();
    const mobile = useMediaQuery({query: `(max-width:${theme.breakpoints.xs})`});
    const {classes} = customStyle();

    return (
        <Header height={HEADER_HEIGHT} className={classes["header"]}>
            <Container style={{height: "100%", display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <Group spacing={"lg"} align={"center"}>
                    <Link to={"/"}>
                        <Logo fill={"var(--primary)"} height={"2rem"}/>
                    </Link>
                    {!mobile &&
                        <Group>
                            <Link to={"/events"} className={classes["web-nav-link"]}>행사</Link>
                            <Link to={""}>SUB1</Link>
                        </Group>
                    }
                </Group>
                {mobile ? <SideNavigator/> : <LoginBtn/>}
            </Container>
        </Header>
    )
}

export default AppBar;