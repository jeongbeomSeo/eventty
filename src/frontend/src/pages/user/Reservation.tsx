import React from "react";
import {Container} from "@mantine/core";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileReservation from "../../components/user/mobile/MobileReservation";
import WebReservation from "../../components/user/web/WebReservation";

function Reservation() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileReservation/> : <WebReservation/>}
        </>
    );
}

export default Reservation;