import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileUserBookings from "../../components/user/mobile/MobileUserBookings";
import WebUserBookings from "../../components/user/web/WebUserBookings";
import {CheckHost} from "../../util/CheckHost";
import {useNavigate} from "react-router-dom";

function Bookings() {
    const isMobile = CheckXsSize();
    const navigate = useNavigate();

    return (
        <>
            {isMobile ? <MobileUserBookings/> : <WebUserBookings/>}
        </>
    );
}

export default Bookings;