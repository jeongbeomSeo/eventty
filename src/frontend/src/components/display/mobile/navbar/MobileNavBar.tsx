import React from "react";
import {Group, Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {IconConfetti, IconHome, IconMenu2, IconSearch, IconUser} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../../../../states/searchDrawerState";
import MobileSearchDrawer from "../MobileSearchDrawer";
import {menuDrawerState} from "../../../../states/menuDrawerState";
import MobileMenuDrawer from "../MobileMenuDrawer";

function MobileNavBar() {
    const {classes} = customStyle();
    const setSearchDrawerValue = useSetRecoilState(searchDrawerState);
    const setMenuDrawerValue = useSetRecoilState(menuDrawerState);

    const ICON_SIZE = "7vw"
    const MENU_LIST = [
        {value: "메뉴", link: "", icon: <IconMenu2 size={ICON_SIZE}/>},
        {value: "행사", link: "/events", icon: <IconConfetti size={ICON_SIZE}/>},
        {value: "홈", link: "/", icon: <IconHome size={ICON_SIZE}/>},
        {value: "검색", link: "", icon: <IconSearch size={ICON_SIZE}/>},
        {value: "마이이벤티", link: "/user", icon: <IconUser size={ICON_SIZE}/>},
    ];

    const items = MENU_LIST.map((item) => (
        item.link !== "" ?
            <UnstyledButton component={Link}
                            to={item.link}
                            className={classes["mobile-nav-link"]}>
                {item.icon}
                <Text fz={"xs"}>{item.value}</Text>
            </UnstyledButton> :
            <UnstyledButton className={classes["mobile-nav-link"]}
                            onClick={() => {
                                item.value === "메뉴" ?
                                    setMenuDrawerValue(true) :
                                    setSearchDrawerValue(true)
                            }}>
                {item.icon}
                <Text fz={"xs"}>{item.value}</Text>
            </UnstyledButton>
    ));

    return (
        <Group position={"apart"} style={{width: "100%", padding: "0 2rem"}}>
            <MobileMenuDrawer/>
            <MobileSearchDrawer/>
            {items}
        </Group>
    );
}

export default MobileNavBar;