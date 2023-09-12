import {useResetRecoilState, useSetRecoilState} from 'recoil';
import { loginState } from '../states/loginState';
import { useEffect } from 'react';
import {Navigate, useNavigate} from 'react-router-dom';
import {userState} from "../states/userState";
import {postLogout} from "../service/user/fetchUser";
import {MessageAlert} from "../util/MessageAlert";

function Logout() {
    const setIsLoggedIn = useSetRecoilState(loginState);
    const resetUserState = useResetRecoilState(userState);
    const navigate = useNavigate();

    useEffect(() => {
        postLogout()
            .then(res => {
                if (res.success){
                    setIsLoggedIn(false);
                    resetUserState();
                    MessageAlert("success", "로그아웃", null);
                }else{
                    MessageAlert("error", "로그아웃 실패", null);
                }
            }).finally(() => navigate("/"));
    }, [])

    return (
        <></>
    );
}

export default Logout;