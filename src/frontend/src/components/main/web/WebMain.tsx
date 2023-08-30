import React from "react";
import WebCarousel from "./WebCarousel";
import {Button, Container, Stack} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import {Link} from "react-router-dom";

function WebMain() {
    return (
        <>
            <WebCarousel/>
            <Container>
                <Stack align={"center"} style={{marginTop: "3rem"}}>
                    <div style={{width:"80%"}}>
                        <SearchBox/>
                    </div>
                    <WebCategoryBtn/>
                    <p style={{height: "100vh"}}>스크롤바 테스트</p>
                </Stack>
            </Container>
        </>
    );
}

export default WebMain;