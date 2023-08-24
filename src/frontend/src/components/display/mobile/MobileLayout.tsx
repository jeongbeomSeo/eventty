import React from "react";
import {Outlet, useLocation} from "react-router-dom";
import TopScrollBtn from "../../common/TopScrollBtn";
import Footer from "../Footer";
import MobileBottomSection from "./navbar/MobileBottomSection";
import MobileTopSection from "./header/MobileTopSection";

function MobileLayout() {
    const {pathname} = useLocation();

    return (
        <>
            <MobileTopSection/>
            <TopScrollBtn/>
            <Outlet/>
            {!pathname.includes("/events/") && <Footer/>}
            <MobileBottomSection/>
        </>
    );
}

export default MobileLayout;