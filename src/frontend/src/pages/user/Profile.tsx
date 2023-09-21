import React, {useEffect} from "react";
import {CheckXsSize} from "../../util/CheckMediaQuery";
import MobileProfile from "../../components/user/mobile/MobileProfile";
import WebProfile from "../../components/user/web/WebProfile";
import {getProfile} from "../../service/user/fetchUser";

function Profile() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileProfile/> : <WebProfile/>}
        </>
    );
}

export default Profile;