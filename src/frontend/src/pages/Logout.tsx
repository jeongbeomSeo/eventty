import { useRecoilState, useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import { useEffect } from 'react';
import {useLocation, useNavigate} from 'react-router-dom';

function Logout() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);

    useEffect(() => {
        setIsLoggedIn(false);
    }, [])

    return (
        <></>
    );
}

export default Logout;