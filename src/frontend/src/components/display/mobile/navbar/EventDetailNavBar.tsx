import React from "react";
import {Box, Button, Flex, Group, Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {IconConfetti, IconHome, IconMenu2, IconSearch, IconUser} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../../../../states/searchDrawerState";
import MobileSearchDrawer from "../MobileSearchDrawer";
import {menuDrawerState} from "../../../../states/menuDrawerState";
import MobileMenuDrawer from "../MobileMenuDrawer";

function EventDetailNavBar() {
    const {classes} = customStyle();

    return (
        <Group position={"apart"}  style={{width: "100%", height: "100%", padding: "0 2rem"}}>
            우와
            <Button className={classes["btn-primary"]} style={{width: "70%", height: "80%"}}>예약하기</Button>
        </Group>
    );
}

export default EventDetailNavBar;