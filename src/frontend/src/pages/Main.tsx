import MobileMain from "../components/main/mobile/MobileMain";
import WebMain from "../components/main/web/WebMain";
import {CheckXsSize} from "../util/CheckMediaQuery";

function Main() {
    const isMobile = CheckXsSize();

    return (
        <>
            {isMobile ? <MobileMain/> : <WebMain/>}
        </>
    );
}

export default Main;