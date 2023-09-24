import React from "react";
import {Container} from "@mantine/core";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileUserBookings from "../../components/user/mobile/MobileUserBookings";
import WebBookings from "../../components/user/web/WebBookings";

function Bookings() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileUserBookings/> : <WebBookings/>}
        </>
    );
}

export default Bookings;