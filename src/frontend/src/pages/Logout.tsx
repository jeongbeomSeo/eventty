import {useRecoilState, useRecoilValue, useResetRecoilState} from 'recoil';
import { loginState } from '../states/loginState';
import { useEffect } from 'react';
import {Navigate, useLocation, useNavigate} from 'react-router-dom';
import {userState} from "../states/userState";

function Logout() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const resetUserState = useResetRecoilState(userState);

    useEffect(() => {
        setIsLoggedIn(false);
        resetUserState();
    }, [])

    return (
        <>
             <Navigate to={"/"}/>
        </>
    );
}

export default Logout;