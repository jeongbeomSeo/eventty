import {Navigate, Outlet, useLocation} from 'react-router-dom';
import {CheckLogin} from "../util/CheckLogin";

function PrivateRoute() {
    const {pathname} = useLocation();
    const isLoggedIn = CheckLogin();

    return isLoggedIn ? <Outlet /> : <Navigate to={"/login"} state={pathname}/>
}

export default PrivateRoute;