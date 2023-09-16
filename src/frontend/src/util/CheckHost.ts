import {useRecoilValue} from "recoil";
import {userState} from "../states/userState";

export const CheckHost = () => {
    const value = useRecoilValue(userState);
    return value.isHost;
}