import React from "react";
import {Box, Container, Divider, Flex, Group, Stack, Title} from "@mantine/core";
import SearchBox from "../../common/SearchBox";
import MobileEventList from "./MobileEventList";
import customStyle from "../../../styles/customStyle";
import MobileCategoryBtn from "./MobileCategoryBtn";
import {Outlet, useParams, useSearchParams} from "react-router-dom";
import {CATEGORY_LIST} from "../../../util/const/categoryList";
import SearchResult from "../SearchResult";

type TParams = {
    category: string;
}

function MobileEvents() {
    const {classes} = customStyle();
    const {category} = useParams<keyof TParams>() as TParams;
    const [searchParams, setSearchParams] = useSearchParams();

    return (
        <>
            <Stack align={"center"} style={{margin: "2rem 0 3vh"}}>
                <Box style={{width: "100%", height: "auto"}}>
                    <Flex gap={"5vw"} className={classes["category-scroll"]}>
                        <MobileCategoryBtn/>
                    </Flex>
                </Box>

                {category &&
                    <Group>
                        <Title>{CATEGORY_LIST[category]}</Title>
                        <Divider my={"2rem"}/>
                    </Group>}
                {searchParams.size > 0 && <SearchResult/>}
            </Stack>
            <Outlet/>
        </>
    );
}

export default MobileEvents;