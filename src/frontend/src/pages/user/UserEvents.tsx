import React from "react";
import {CheckMobile} from "../../util/CheckMobile";
import WebUserEvents from "../../components/user/web/WebUserEvents";
import MobileUserEvents from "../../components/user/mobile/MobileUserEvents";

function UserEvents() {
    const isMobile = CheckMobile();

    return (
        <>
            {isMobile ? <MobileUserEvents/> : <WebUserEvents/>}
        </>
    );
}

export default UserEvents;