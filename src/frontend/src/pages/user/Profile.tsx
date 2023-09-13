import React from "react";
import {CheckMobile} from "../../util/CheckMobile";
import MobileProfile from "../../components/user/mobile/MobileProfile";
import WebProfile from "../../components/user/web/WebProfile";

function Profile() {
    const isMobile = CheckMobile();

    return (
        <>
            {isMobile ? <MobileProfile/> : <WebProfile/>}
        </>
    );
}

export default Profile;