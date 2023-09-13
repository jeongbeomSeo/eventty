import {Container, Flex, Group, Header, Text, UnstyledButton,} from "@mantine/core";
import {Link, useLocation, useNavigate, useNavigation} from "react-router-dom";
import Logo from "../../../common/Logo";
import customStyle from "../../../../styles/customStyle";
import React from "react";
import {IconChevronLeft, IconHome, IconSearch} from "@tabler/icons-react";
import {useRecoilState, useSetRecoilState} from "recoil";
import {searchDrawerState} from "../../../../states/searchDrawerState";

function EventDetailHeader() {
    const navigate = useNavigate();
    const {state} = useLocation();
    const setSearchDrawer = useSetRecoilState(searchDrawerState);

    const {classes} = customStyle();

    return (
        <Group position={"apart"} style={{width:"100%"}}>
            <UnstyledButton className={classes["mobile-nav-link"]}
                            onClick={() => navigate(state)}>
                <IconChevronLeft size={"3.5vh"}/>
            </UnstyledButton>
            <Group>
                <UnstyledButton className={classes["mobile-nav-link"]}
                                onClick={() => setSearchDrawer(prev => !prev)}>
                    <IconSearch size={"3.5vh"}/>
                </UnstyledButton>
                <UnstyledButton className={classes["mobile-nav-link"]}
                                component={Link} to={"/"}>
                    <IconHome size={"3.5vh"}/>
                </UnstyledButton>
            </Group>
        </Group>
    )
}

export default EventDetailHeader;