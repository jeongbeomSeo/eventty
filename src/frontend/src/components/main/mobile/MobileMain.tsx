import React from "react";
import MobileCarousel from "./MobileCarousel";
import {Box, Container, Flex, Stack} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";

function MobileMain() {
    const {classes} = customStyle();

    return (
        <>
            <MobileCarousel/>
            <Container>
                <Stack align={"center"} style={{marginTop: "3rem"}}>
                    <SearchBox/>
                    <Box style={{width:"100%"}}>
                        <Flex gap={"7vw"} className={classes["category-scroll"]}>
                            <MobileCategoryBtn/>
                        </Flex>
                    </Box>
                    <p style={{height: "100vh"}}>스크롤바 테스트</p>
                </Stack>
            </Container>
        </>
    );
}

export default MobileMain;