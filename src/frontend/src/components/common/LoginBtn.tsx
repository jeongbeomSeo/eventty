import React from "react";
import {Button} from "@mantine/core";
import {Link} from "react-router-dom";
import {useRecoilValue} from "recoil";
import {loginState} from "../../states/loginState";
import customStyles from "../../styles/customStyle";
import {userState} from "../../states/userState";

function LoginBtn() {
    const isLoggedIn = useRecoilValue(loginState);
    const userStateValue = useRecoilValue(userState);
    const loginLink = isLoggedIn ?
        {to: "/logout", value: "로그아웃"} :
        {to: "/login", value: "로그인"};

    const {classes} = customStyles();

    return (
        <div style={{alignItems:"end"}}>
            {(isLoggedIn && userStateValue.isHost) &&
                <Button component={Link}
                        to={"/"}
                        className={classes["btn-primary-outline"]}
                        style={{padding: "0 2rem", marginRight: "1rem"}}>
                    주최하기
                </Button>
            }
            <Button component={Link} to={loginLink.to} className={classes["btn-primary"]}>{loginLink.value}</Button>
        </div>
    );
}

export default LoginBtn;