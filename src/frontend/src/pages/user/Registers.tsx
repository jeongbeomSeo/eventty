import React from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import WebRegister from "../../components/user/web/WebRegister";
import MobileEventsInfo from "../../components/user/mobile/MobileEventsInfo";

function Registers() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileEventsInfo/> : <WebRegister/>}
        </>
    );
}

export default Registers;