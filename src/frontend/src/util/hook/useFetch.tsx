import {
    deleteAccount,
    postChangePassword, postFindEmail, postFindPassword,
    postLogout, postProfile
} from "../../service/user/fetchUser";
import {MessageAlert} from "../MessageAlert";
import {useRecoilState, useRecoilValue, useResetRecoilState, useSetRecoilState} from "recoil";
import {loginState} from "../../states/loginState";
import {userState} from "../../states/userState";
import {useNavigate} from "react-router-dom";
import {deleteEvent, postEvent} from "../../service/event/fetchEvent";
import {IChangePW, IFindEmail, IFindPassword} from "../../types/IUser";
import {modals} from "@mantine/modals";
import {menuDrawerState} from "../../states/menuDrawerState";
import {loadingState} from "../../states/loadingState";

export function useFetch() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const [loading, setLoading] = useRecoilState(loadingState);
    const setMenuDrawer = useSetRecoilState(menuDrawerState);
    const [userStateValue, setUserStateValue] = useRecoilState(userState);
    const resetUserState = useResetRecoilState(userState);
    const navigate = useNavigate();

    // 이메일 찾기
    const findEmailFetch = (data: IFindEmail) => {
        setLoading(true);

        postFindEmail(data)
            .then(res => {
                if (res.success) {
                    console.log(res);
                    navigate("/find/result", {state: res.successResponseDTO.data});
                } else {
                    MessageAlert("error", "해당 계정을 찾을 수 없습니다", "다시 시도해주세요");
                }
            }).finally(() => setLoading(prev => !prev));
    }

    // 비밀번호 찾기
    const findPasswordFetch = (data: IFindPassword) => {
        setLoading(true);

        postFindPassword(data)
            .then(res => {
                if (res.success) {
                    navigate("/find/result", {state: res.success});
                } else {
                    MessageAlert("error", "해당 계정을 찾을 수 없습니다", "다시 시도해주세요");
                }
            }).finally(() => setLoading(prev => !prev));
    }

    // 로그아웃
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
                        MessageAlert("error", "로그아웃 실패", null)
                    }
                }).finally(() => {
                navigate("/");
                setMenuDrawer(false);
            })
        }
    }

    // 내 정보 수정
    const changeProfileFetch = (data: FormData) => {
        postProfile(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "내 정보가 변경되었습니다", null);

                    if (res.successResponseDTO && res.successResponseDTO.data.imagePath) {
                        sessionStorage.setItem("IMG_PATH", res.successResponseDTO.data.imagePath);
                        setUserStateValue(prev => {
                            return {...prev, imagePath: res.successResponseDTO.data.imagePath}
                        })
                    }else{
                        sessionStorage.setItem("IMG_PATH", "");
                        setUserStateValue(prev => {
                            return {...prev, imagePath: ""}
                        })
                    }
                } else {
                    MessageAlert("error", "내 정보 변경 실패", null);
                }
            });
    }

    // 내 정보 비밀번호 변경
    const changePasswordFetch = (data: IChangePW) => {
        postChangePassword(data)
            .then(res => {
                if (res === 200) {
                    MessageAlert("success", "비밀번호가 변경되었습니다", null);
                } else {
                    MessageAlert("error", "비밀번호 변경 실패", null);
                }
            }).finally(() => modals.closeAll());
    }

    // 회원 탈퇴
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

    // 행사 주최
    const createEventFetch = (data: FormData) => {
        postEvent(data)
            .then(res => {
                if (res.isSuccess) {
                    MessageAlert("success", "작성 성공", null);
                    navigate("/");
                } else {
                    MessageAlert("error", "작성 실패", null);
                }
            }).finally(() => setLoading(false));
    }

    // 행사 취소
    const deleteEventFetch = (data: number) => {
        deleteEvent(data)
            .then(res => {
                if (res === 200) {
                    MessageAlert("success", "행사 취소", null);
                    navigate("/events");
                } else {
                    MessageAlert("error", "행사 취소 실패", null);
                }
            })
    }

    return {
        logoutFetch,
        deleteAccountFetch,
        deleteEventFetch,
        changePasswordFetch,
        changeProfileFetch,
        createEventFetch,
        findEmailFetch,
        findPasswordFetch,
    };
}