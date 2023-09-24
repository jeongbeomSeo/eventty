import {Button} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {GoogleOAuthProvider, useGoogleLogin} from "@react-oauth/google";
import {IGoogleLogin} from "../../types/IUser";
import {postGoogleLogin} from "../../service/user/fetchUser";

function GoogleBtn() {
    const {classes} = customStyle();

    const login = useGoogleLogin({
        onSuccess: tokenResponse => {
            console.log(tokenResponse);
            const accessToken:IGoogleLogin = {OAuth_AccessToken:tokenResponse.access_token!};
            postGoogleLogin(accessToken);
        },
    });

    return (
        <Button style={{height: "2.6rem"}} className={classes["btn-gray-outline"]} onClick={() => login()}>
            <img src={`${process.env.PUBLIC_URL}/images/google_normal.svg`} style={{paddingRight: "0.5rem"}}/>
            구글 로그인
        </Button>
    );
}

export default GoogleBtn;