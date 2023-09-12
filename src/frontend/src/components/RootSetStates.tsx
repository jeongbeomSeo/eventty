import React, {useEffect} from "react";
import {useRecoilValue, useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {useLocation, useNavigation} from "react-router-dom";
import ScrollToTop from "./ScrollToTop";
import {menuDrawerState} from "../states/menuDrawerState";
import {Notifications} from "@mantine/notifications";
import {useMediaQuery} from "react-responsive";
import {LoadingOverlay, useMantineTheme} from "@mantine/core";
import {loadingState} from "../states/loadingState";

function RootSetStates() {
    const {state} = useNavigation();
    const {pathname} = useLocation();
    const loadingValue = useRecoilValue(loadingState);
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
            {/* Router Loader 로딩 오버레이 */}
            {(state === "loading" || loadingValue) &&
                <LoadingOverlay visible
                                loaderProps={{size: "md", color: "var(--primary)", variant: "dots"}}
                                overlayBlur={2}
                                style={{position:"fixed"}}
                />
            }

            <Notifications position={mobile ? "top-center" : "bottom-right"}/>
            <ScrollToTop/>
        </>
    );
}

export default RootSetStates;