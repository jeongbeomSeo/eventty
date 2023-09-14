import React from "react";
import {CheckXsSize} from "../util/CheckMediaQuery";
import WebUserLayout from "../components/user/WebUserLayout";
import MobileUserLayout from "../components/user/MobileUserLayout";

function User() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileUserLayout/>: <WebUserLayout/>}
        </>
    );
}

export default User;