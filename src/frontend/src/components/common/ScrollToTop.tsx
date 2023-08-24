import React, { useEffect } from "react";
import {Outlet, useLocation, useNavigation} from "react-router-dom";
import {LoadingOverlay} from "@mantine/core";

export default function ScrollToTop() {
    const {state} = useNavigation();
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return (
        <>
            {state === "loading" &&
                <LoadingOverlay visible
                                loaderProps={{size: "md", color: "var(--primary)", variant: "dots"}}
                                overlayBlur={2}/>
            }
            <Outlet/>
        </>
    );
};