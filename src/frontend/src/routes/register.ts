import {CheckHost} from "../util/CheckHost";
import {redirect} from "react-router-dom";
import {getHostEvents} from "../service/event/fetchEvent";

export const loader = () => {
    if (!CheckHost) {
        redirect("/login");
    }
    return getHostEvents();
}