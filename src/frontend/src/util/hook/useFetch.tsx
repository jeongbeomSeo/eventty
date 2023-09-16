import {deleteAccount, patchChangePassword, postLogin, postLogout} from "../../service/user/fetchUser";
import {MessageAlert} from "../MessageAlert";
import {useRecoilState, useResetRecoilState, useSetRecoilState} from "recoil";
import {loginState} from "../../states/loginState";
import {userState} from "../../states/userState";
import {useNavigate} from "react-router-dom";
import {loadingState} from "../../states/loadingState";
import {deleteEvent} from "../../service/event/fetchEvent";
import {IChangePW} from "../../types/IUser";
import {modals} from "@mantine/modals";

export function useFetch() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const resetUserState = useResetRecoilState(userState);
    const navigate = useNavigate();
    
    const deleteEventFetch = (data: number) => {
        deleteEvent(data)
            .then(res => {
                if (res === 200){
                    MessageAlert("success", "행사 취소", null);
                    navigate("/events");
                }else {
                    MessageAlert("error", "행사 취소 실패", null);
                }
            })
    }

    const logoutFetch = () => {
        if (isLoggedIn) {
            postLogout()
                .then(res => {
                    if (res === 200) {
                        setIsLoggedIn(false);
                        resetUserState();
                        sessionStorage.clear();
                        MessageAlert("success", "로그아웃", null);
                    } else {
                        MessageAlert("error", "로그아웃 실패", null);
                    }
                }).finally(() => {
                navigate("/");
            });
        }
    }

    const changePasswordFetch = (data:IChangePW) => {
        patchChangePassword(data)
            .then(res => {
                if (res === 200){
                    window.location.reload();
                    MessageAlert("success", "비밀번호가 변경되었습니다", null);
                }else {
                    MessageAlert("error", "비밀번호 변경 실패", null);
                }
            }).finally(() => modals.closeAll());
    }

    const deleteAccountFetch = () => {
        if (isLoggedIn) {
            deleteAccount()
                .then(res => {
                    if (res === 200) {
                        navigate("/");
                        setIsLoggedIn(false);
                        resetUserState();
                        sessionStorage.clear();
                        MessageAlert("success", "회원 탈퇴", null);
                    } else {
                        MessageAlert("error", "회원 탈퇴 실패", null);
                    }
                });
        }
    }

    return {logoutFetch, deleteAccountFetch, deleteEventFetch, changePasswordFetch};
}