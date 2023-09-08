import React from "react";
import {useMediaQuery} from "react-responsive";
import {useMantineTheme} from "@mantine/core";
import MobileLayout from "./mobile/MobileLayout";
import WebLayout from "./web/WebLayout";
import {Notifications} from "@mantine/notifications";

function Layout() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <>
            {mobile ? <MobileLayout/> : <WebLayout/>}
        </>
    );
}

export default Layout;