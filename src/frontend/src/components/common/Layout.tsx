import {Outlet} from "react-router-dom";
import TopScrollBtn from "./TopScrollBtn";
import Footer from "./Footer";
import {useMediaQuery} from "react-responsive";
import {useMantineTheme} from "@mantine/core";
import MobileNavBar from "./MobileNavBar";
import AppBar from "./AppBar";

function Layout() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <>
            <AppBar/>
            <TopScrollBtn/>
            <Outlet/>
            <Footer/>
            {mobile && <MobileNavBar/>}
        </>
    );
}

export default Layout;