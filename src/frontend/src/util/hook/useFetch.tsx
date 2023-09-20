import {
    deleteAccount,
    postChangePassword,
    postLogout, postProfile
} from "../../service/user/fetchUser";
import {MessageAlert} from "../MessageAlert";
import {useRecoilState, useResetRecoilState, useSetRecoilState} from "recoil";
import {loginState} from "../../states/loginState";
import {userState} from "../../states/userState";
import {useNavigate} from "react-router-dom";
import {deleteEvent, postEvent} from "../../service/event/fetchEvent";
import {IChangePW} from "../../types/IUser";
import {modals} from "@mantine/modals";
import {menuDrawerState} from "../../states/menuDrawerState";

export function useFetch() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const resetUserState = useResetRecoilState(userState);
    const navigate = useNavigate();

    const createEventFetch = (data: FormData) => {
        postEvent(data)
            .then(res => {
                if (res.success){
                    MessageAlert("success", "작성 성공", null);
                    navigate("/");
                }else {
                    MessageAlert("error", "작성 실패", null);
                }
            })
    }
    
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
                setMenuDrawer(false);
            });
        }
    }
    
    const changeProfileFetch = (data:FormData) => {
        postProfile(data)
            .then(res => {
                if (res === 200){
                    MessageAlert("success", "내 정보가 변경되었습니다", null);
                }else{
                    MessageAlert("error", "내 정보 변경 실패", null);
                }
            })
    }

    const changePasswordFetch = (data:IChangePW) => {
        postChangePassword(data)
            .then(res => {
                if (res === 200){
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

    return {logoutFetch, deleteAccountFetch, deleteEventFetch, changePasswordFetch, changeProfileFetch, createEventFetch};
}