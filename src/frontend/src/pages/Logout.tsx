import { useRecoilState, useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Logout() {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);

    useEffect(() => {
        setIsLoggedIn(false);
        navigate("/");
    }, [])

    return (
        <></>
    );
}

export default Logout;