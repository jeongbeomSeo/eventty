import React from "react";
import {CheckMobile} from "../util/CheckMobile";
import WebUserLayout from "../components/user/web/WebUserLayout";
import MobileUserLayout from "../components/user/mobile/MobileUserLayout";
import {Link, Navigate, redirect, useLocation, useNavigate} from "react-router-dom";
import {CheckLogin} from "../util/CheckLogin";

function User() {
    const isMobile = CheckMobile();

    return (
        <>
            {isMobile ? <MobileUserLayout/>: <WebUserLayout/>}
        </>
    );
}

export default User;