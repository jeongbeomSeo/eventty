import React from "react";
import {Button} from "@mantine/core";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {useRecoilValue} from "recoil";
import {loginState} from "../../../../states/loginState";
import customStyles from "../../../../styles/customStyle";
import {userState} from "../../../../states/userState";

function WebLoginBtn() {
    const navigate = useNavigate();
    const {pathname} = useLocation();

    const {classes} = customStyles();

    return (
        <Button
            className={classes["btn-primary"]}
            onClick={() => navigate("/login", {state: pathname})}>
            로그인
        </Button>
    );
}

export default WebLoginBtn;