import {getProfile} from "../service/user/fetchUser";
import {CheckLogin} from "../util/CheckLogin";
import {redirect} from "react-router-dom";

export const loader = () => {
    if (!CheckLogin) redirect("/login");
    return getProfile();
}