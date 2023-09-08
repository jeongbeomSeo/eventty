import {useResetRecoilState, useSetRecoilState} from 'recoil';
import { loginState } from '../states/loginState';
import { useEffect } from 'react';
import {Navigate} from 'react-router-dom';
import {userState} from "../states/userState";

function Logout() {
    const setIsLoggedIn = useSetRecoilState(loginState);
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