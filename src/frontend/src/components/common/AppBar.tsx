import {Container, Divider, Group, Header, useMantineTheme} from "@mantine/core";
import {Link} from "react-router-dom";
import LoginBtn from "./LoginBtn";
import Logo from "./Logo";
import customStyle from "../../styles/customStyle";
import SideNavigator from "./SideNavigator";
import {useMediaQuery} from "react-responsive";

const HEADER_HEIGHT = "65px";

function AppBar() {
    const theme = useMantineTheme();
    const mobile = useMediaQuery({query: `(max-width:${theme.breakpoints.sm})`});

    const {classes} = customStyle();

    return (
        <Header height={HEADER_HEIGHT}
                style={{position: "sticky", zIndex: 2, boxShadow: "0 2px 6px rgba(0, 0, 0, 0.1)"}}>
            <Container style={{height: "100%", display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <Group spacing={"lg"}>
                    <Link to={"/"}>
                        <Logo fill={"var(--primary)"} height={"2rem"}/>
                    </Link>
                    {!mobile &&
                        <Group>
                            <Link to={"/"}>MENU1</Link>
                            <Divider orientation="vertical"/>
                            <Link to={"/"}>MENU2</Link>
                            <Divider orientation="vertical"/>
                            <Link to={"/"}>MENU3</Link>
                        </Group>
                    }
                </Group>
                {mobile ? <SideNavigator/> : <LoginBtn/>}
            </Container>
        </Header>
    )
}

export default AppBar;