import MobileMain from "../components/main/mobile/MobileMain";
import WebMain from "../components/main/web/WebMain";
import {CheckMobile} from "../util/CheckMobile";

function Main() {
    const isMobile = CheckMobile();

    return (
        <>
            {isMobile ? <MobileMain/> : <WebMain/>}
        </>
    );
}

export default Main;