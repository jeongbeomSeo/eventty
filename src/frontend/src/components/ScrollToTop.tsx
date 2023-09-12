import React, { useEffect } from "react";
import {Outlet, useLocation, useNavigation} from "react-router-dom";
import {LoadingOverlay} from "@mantine/core";

export default function ScrollToTop() {
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return (
        <>
            <Outlet/>
        </>
    );
};