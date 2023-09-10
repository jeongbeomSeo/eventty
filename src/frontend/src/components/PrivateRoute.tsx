import { useRecoilValue } from 'recoil';
import { loginState } from '../states/loginState';
import {Navigate, Outlet, useLocation, useNavigate} from 'react-router-dom';
import {stat} from "fs";
import {CheckLogin} from "../util/CheckLogin";

function PrivateRoute() {
    const {state} = useLocation();
    const isLoggedIn = CheckLogin();

    return isLoggedIn ? <Outlet /> : <Navigate to={state} />
}

export default PrivateRoute;