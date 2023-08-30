import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {useLocation} from "react-router-dom";
import ScrollToTop from "./ScrollToTop";
import {menuDrawerState} from "../states/menuDrawerState";

function RootSetStates() {
    const {pathname} = useLocation();
    const setSearchDrawer = useSetRecoilState(searchDrawerState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);

    useEffect(() => {
        return () => {
            setSearchDrawer(false);
            setMenuDrawer(false);
        }
    }, [pathname]);

    return (
        <>
            <ScrollToTop/>
        </>
    );
}

export default RootSetStates;