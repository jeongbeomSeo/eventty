import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import WebEventsInfo from "../../components/user/web/WebEventsInfo";
import MobileEventsInfo from "../../components/user/mobile/MobileEventsInfo";

function EventsInfo() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileEventsInfo/> : <WebEventsInfo/>}
        </>
    );
}

export default EventsInfo;