import { useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import {Navigate, Outlet, useLocation} from 'react-router-dom';

function PublicRoute() {
    const {state} = useLocation();
    const isLoggedIn = useRecoilValue(loginState);

    return isLoggedIn ? <Navigate to={state} /> : <Outlet />
}

export default PublicRoute;