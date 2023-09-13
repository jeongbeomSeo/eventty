import React from "react";
import {CheckMobile} from "../../util/CheckMobile";
import WebEventsInfo from "../../components/user/web/WebEventsInfo";
import MobileEventsInfo from "../../components/user/mobile/MobileEventsInfo";

function EventsInfo() {
    const isMobile = CheckMobile();

    return (
        <>
            {isMobile ? <MobileEventsInfo/> : <WebEventsInfo/>}
        </>
    );
}

export default EventsInfo;