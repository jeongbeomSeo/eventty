import React from "react";
import {Outlet} from "react-router-dom";
import TopScrollBtn from "../../common/TopScrollBtn";
import Footer from "../Footer";
import WebHeader from "./WebHeader";

function WebLayout() {
    return (
        <>
            <WebHeader/>
            <TopScrollBtn/>
            <Outlet/>
            <Footer/>
        </>
    );
}

export default WebLayout;