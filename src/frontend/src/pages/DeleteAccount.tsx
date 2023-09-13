import React, {useEffect} from "react";
import {deleteAccount} from "../service/user/fetchUser";
import {MessageAlert} from "../util/MessageAlert";
import {useNavigate} from "react-router-dom";
import {useResetRecoilState, useSetRecoilState} from "recoil";
import {loginState} from "../states/loginState";
import {userState} from "../states/userState";

function DeleteAccount() {
    const navigate = useNavigate();
    const setIsLoggedIn = useSetRecoilState(loginState);
    const resetUserState = useResetRecoilState(userState);

    useEffect(() => {
        deleteAccount()
            .then(res => {
                if (res === 200){
                    setIsLoggedIn(false);
                    resetUserState();
                    sessionStorage.clear();
                    MessageAlert("success", "회원 탈퇴", null);
                }else{
                    MessageAlert("error", "회원 탈퇴 실패", null);
                }
            }).finally(() => navigate("/"));
    })

    return (
        <></>
    );
}

export default DeleteAccount;