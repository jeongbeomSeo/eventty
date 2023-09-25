import {Link} from "react-router-dom";
import Logo from "../../../common/Logo";
import React from "react";
import {Flex} from "@mantine/core";
import customStyle from "../../../../styles/customStyle";

function MobileHeader() {
    const {classes} = customStyle();

    return (
        <Flex justify={"center"} style={{width: "100%"}}>
            <Link to={"/"}>
                <Logo fill={"var(--primary)"} height={"3.5vh"}/>
            </Link>
        </Flex>
    )
}

export default MobileHeader;