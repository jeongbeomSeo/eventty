import React, {useEffect} from "react";
import {useRecoilState, useRecoilValue, useSetRecoilState} from "recoil";
import {searchDrawerState} from "../states/searchDrawerState";
import {Outlet, ScrollRestoration, useLocation, useNavigation} from "react-router-dom";
import {menuDrawerState} from "../states/menuDrawerState";
import {Notifications} from "@mantine/notifications";
import {useMediaQuery} from "react-responsive";
import {LoadingOverlay, useMantineTheme} from "@mantine/core";
import {loadingState} from "../states/loadingState";
import {loginState} from "../states/loginState";
import {userState} from "../states/userState";
import {eventTicketDrawerState} from "../states/eventTicketDrawerState";
import {useModal} from "../util/hook/useModal";
import {ModalsProvider} from "@mantine/modals";

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
    const {searchModal} = useModal();

    useEffect(() => {
        return () => {
            setSearchDrawer(false);
            setMenuDrawer(false);
            setEventTicketDrawer(false);
        }
    }, [pathname]);

    // 로그인 확인
    useEffect(() => {
        const email = sessionStorage.getItem("EMAIL");
        const authority = sessionStorage.getItem("AUTHORITY");
        const userId = sessionStorage.getItem("USER_ID");

        if (email && authority && !loginStateValue) {
            setloginStateValue(true);
            setUserState({email: email, isHost: authority === "HOST", userId: userId!});
        }
    });

    return (
        <>
            <ModalsProvider>
                {/* Router Loader 로딩 오버레이 */}
                {(state === "loading" || loadingValue) &&
                    <LoadingOverlay visible
                                    loaderProps={{size: "md", color: "var(--primary)", variant: "dots"}}
                                    overlayBlur={2}
                                    style={{position: "fixed"}}
                                    zIndex={1002}
                    />
                }
                <Notifications position={mobile ? "top-center" : "bottom-right"}/>
                <ScrollRestoration/>
                <Outlet/>
            </ModalsProvider>
        </>
    );
}

export default RootSetStates;