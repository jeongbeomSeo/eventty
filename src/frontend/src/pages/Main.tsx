import {useRecoilValue, useSetRecoilState} from 'recoil';
import {userState} from '../states/userState';
import {loginState} from '../states/loginState';
import {Container, Stack, useMantineTheme} from "@mantine/core";
import WebCarousel from "../components/main/WebCarousel";
import SearchBox from "../components/common/SearchBox";
import MobileCarousel from "../components/main/MobileCarousel";
import {useMediaQuery} from "react-responsive";
import {navBarState} from "../states/navBarState";
import CategoryBtn from "../components/event/CategoryBtn";

function Main() {
    const userStateValue = useRecoilValue(userState);
    const isLoggedIn = useRecoilValue(loginState);

    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <>
            {mobile ? <MobileCarousel/> : <WebCarousel/>}
            <Container>
                <Stack align={"center"} style={{marginTop: "3rem"}}>
                    <SearchBox/>
                    <CategoryBtn/>
                    <p style={{height: "100vh"}}>스크롤바 테스트</p>
                </Stack>
            </Container>
        </>
    );
}

export default Main;