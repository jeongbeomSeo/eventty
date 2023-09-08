import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {useLocation} from "react-router-dom";
import ScrollToTop from "./ScrollToTop";
import {menuDrawerState} from "../states/menuDrawerState";
import {Notifications} from "@mantine/notifications";
import {useMediaQuery} from "react-responsive";
import {useMantineTheme} from "@mantine/core";

function RootSetStates() {
    const {pathname} = useLocation();
    const setSearchDrawer = useSetRecoilState(searchDrawerState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    useEffect(() => {
        return () => {
            setSearchDrawer(false);
            setMenuDrawer(false);
        }
    }, [pathname]);

    return (
        <>
            <Notifications style={{marginBottom: mobile ? "8vh" : ""}}/>
            <ScrollToTop/>
        </>
    );
}

export default RootSetStates;