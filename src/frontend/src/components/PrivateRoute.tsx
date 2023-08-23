import { useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import {Navigate, Outlet, useLocation, useNavigate} from 'react-router-dom';
import {stat} from "fs";

function PrivateRoute() {
    const {state} = useLocation();
    const isLoggedIn = useRecoilValue(loginState);

    return isLoggedIn ? <Outlet /> : <Navigate to={state} />
}

export default PrivateRoute;