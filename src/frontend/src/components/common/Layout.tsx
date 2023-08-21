import AppBar from "./AppBar";
import {Outlet} from "react-router-dom";
import {Container} from "@mantine/core";
import TopScrollBtn from "./TopScrollBtn";

function Layout() {
    return (
        <>
            <AppBar/>
            <TopScrollBtn/>
            <Outlet/>
        </>
    );
}

export default Layout;