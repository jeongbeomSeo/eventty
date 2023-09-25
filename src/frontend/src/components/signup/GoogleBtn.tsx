import {Button} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useGoogleLogin} from "@react-oauth/google";
import {useFetch} from "../../util/hook/useFetch";
import {useRecoilState} from "recoil";
import {loadingState} from "../../states/loadingState";
import {ISocialLogin} from "../../types/IUser";

function GoogleBtn() {
    const {classes} = customStyle();
    const {googleLoginFetch} = useFetch();
    const [loading, setLoading] = useRecoilState(loadingState);

    const login = useGoogleLogin({
        onSuccess: codeResponse => {
            const code:ISocialLogin = {
                code: codeResponse.code,
            }
            googleLoginFetch(code);
        },
        flow: "auth-code",
    });

    return (
        <Button
                style={{
                    height: "50px",
                    width: "50px",
                    backgroundImage: `url(${process.env.PUBLIC_URL}/images/google_normal.svg)`,
                    backgroundRepeat: "no-repeat",
                    backgroundPosition: "center",
                    backgroundSize: "26px",
                }}
                className={classes["btn-gray-outline"]}
                onClick={() => login()}>
        </Button>
    );
}

export default GoogleBtn;