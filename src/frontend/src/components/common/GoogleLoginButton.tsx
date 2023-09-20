import React from "react";
import {GoogleLogin, googleLogout, GoogleOAuthProvider, useGoogleLogin} from "@react-oauth/google";
import {useSetRecoilState} from "recoil";
import {loginState} from "../../states/loginState";
import {Button} from "@mantine/core";

function GoogleLoginButton() {
    const setLogin = useSetRecoilState(loginState);

    return (
        <>
            <GoogleOAuthProvider clientId={process.env["REACT_APP_GOOGLE_CLIENT_ID"]!}>
                <GoogleLogin
                    onSuccess={credentialResponse => {
                        console.log(credentialResponse);
                    }}
                    onError={() => {
                        console.log('Login Failed');
                    }}
                />
            </GoogleOAuthProvider>
        </>
    );
}

export default GoogleLoginButton;