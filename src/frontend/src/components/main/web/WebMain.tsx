import React from "react";
import WebCarousel from "./WebCarousel";
import {Button, Container, Group, Stack} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import {MessageAlert} from "../../../util/MessageAlert";

function WebMain() {
    return (
        <>
            <WebCarousel/>
            <Container>
                <Stack align={"center"} style={{marginTop: "3rem"}}>
                    <div style={{width: "80%"}}>
                        <SearchBox/>
                    </div>
                    <WebCategoryBtn/>
                    <Group>
                        <Button onClick={() => MessageAlert("success", "title", "message")}>성공 메세지 테스트</Button>
                        <Button onClick={() => MessageAlert("error", "title", "message")}>에러 메세지 테스트</Button>
                        <Button onClick={() => MessageAlert("notice", "title", "message")}>경고 메세지 테스트</Button>
                        <Button onClick={() => MessageAlert("info", "title", "message")}>인포 메세지 테스트</Button>
                    </Group>
                    <p style={{height: "100vh"}}>스크롤바 테스트</p>
                </Stack>
            </Container>
        </>
    );
}

export default WebMain;