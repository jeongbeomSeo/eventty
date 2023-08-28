import {Link} from "react-router-dom";
import Logo from "../../../common/Logo";
import React from "react";

function MobileHeader() {
    return (
        <Link to={"/"}>
            <Logo fill={"var(--primary)"} height={"4vh"}/>
        </Link>
    )
}

export default MobileHeader;