import { useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import { Navigate, Outlet } from 'react-router-dom';

function PrivateRoute() {
    const isLoggedIn = useRecoilValue(loginState);

    return isLoggedIn ? <Outlet /> : <Navigate to={"/"} />
}

export default PrivateRoute;