import {Link} from "react-router-dom";
import Logo from "../../../common/Logo";
import React from "react";
import {Flex, Group, Indicator, UnstyledButton} from "@mantine/core";
import {IconBell} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import {CheckLogin} from "../../../../util/CheckLogin";

function MobileHeader() {
    const {classes} = customStyle();
    const isLoggedIn = CheckLogin();

    return (
        <Group position={"apart"} style={{width: "100%"}}>
            <Link to={"/"}>
                <Logo fill={"var(--primary)"} height={"3.5vh"}/>
            </Link>

            {isLoggedIn &&
                <UnstyledButton>
                    <Indicator color={"red"} offset={3} inline withBorder disabled={false}>
                        <IconBell size={"1.8rem"} className={classes["mobile-nav-link"]}/>
                    </Indicator>
                </UnstyledButton>
            }
        </Group>
    )
}

export default MobileHeader;