import {Navigate, Outlet, useLocation} from 'react-router-dom';
import {CheckLogin} from "../util/CheckLogin";
import {CheckHost} from "../util/CheckHost";

function PrivateRoute() {
    const {pathname} = useLocation();

    return CheckLogin() ? <Outlet /> : <Navigate to={"/login"} state={pathname}/>
}

export default PrivateRoute;