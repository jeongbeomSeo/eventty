import React, {useEffect} from "react";
import {useRecoilState, useRecoilValue, useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {useLocation, useNavigation} from "react-router-dom";
import ScrollToTop from "./ScrollToTop";
import {menuDrawerState} from "../states/menuDrawerState";
import {Notifications} from "@mantine/notifications";
import {useMediaQuery} from "react-responsive";
import {LoadingOverlay, useMantineTheme} from "@mantine/core";
import {loadingState} from "../states/loadingState";
import {loginState} from "../states/loginState";
import {userState} from "../states/userState";
import {eventTicketDrawerState} from "../states/eventTicketDrawerState";

function RootSetStates() {
    const {state} = useNavigation();
    const {pathname} = useLocation();
    const loadingValue = useRecoilValue(loadingState);
    const [loginStateValue, setloginStateValue] = useRecoilState(loginState);
    const setUserState = useSetRecoilState(userState);
    const setSearchDrawer = useSetRecoilState(searchDrawerState);
    const setEventTicketDrawer = useSetRecoilState(eventTicketDrawerState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    useEffect(() => {
        return () => {
            setSearchDrawer(false);
            setMenuDrawer(false);
            setEventTicketDrawer(false);
        }
    }, [pathname]);

    // 로그인 확인
    useEffect(() => {
        const email = sessionStorage.getItem("email");
        const authorities = sessionStorage.getItem("authorities");

        if (email && authorities && !loginStateValue){
            setloginStateValue(true);
            setUserState({email: email, isHost: authorities === "HOST"});
        }
    });

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