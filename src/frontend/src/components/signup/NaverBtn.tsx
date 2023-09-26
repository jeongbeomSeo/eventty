import {useEffect} from "react";
import {Button} from "@mantine/core";
import {Link, useLocation} from "react-router-dom";
import {MessageAlert} from "../../util/MessageAlert";

declare global {
    interface Window {
        naver: any;
    }
}

/*function NaverBtn() {
    const {naver} = window;

    const initializeNaverLogin = () => {
        const naverLogin = new window.naver.LoginWithNaverId({
            clientId: `${process.env["REACT_APP_NAVER_CLIENT_ID"]}`,
            callbackUrl: `${process.env["REACT_APP_NAVER_REDIRECT_URL"]}`,
            isPopup: true,
            loginButton: {color: "green", type: 1, height: 50},
            callbackHandle: true,
        });
        naverLogin.init();
    };

    const userAccessToken = () => {
        window.location.href.includes("access_token") && getToken();
    }
    const getToken = () => {
        const token = window.location.href.split('=')[1].split('&')[0];
        console.log(token);
    }

    useEffect(() => {
        initializeNaverLogin();
        userAccessToken();
    }, []);

    return(
        <div id={"naverIdLogin"}></div>
    )
}*/

function NaverBtn() {
    const handleOnClick = () => {
        window.open(`https://nid.naver.com/oauth2.0/authorize?client_id=${process.env["REACT_APP_NAVER_CLIENT_ID"]}&response_type=code&redirect_uri=${process.env["REACT_APP_NAVER_REDIRECT_URL"]}`, "eventty", "_blank");
    }

    return (
        <Button onClick={() => handleOnClick()}>
        </Button>
    );
}

export default NaverBtn;