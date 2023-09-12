import React, {useEffect, useState} from "react";
import {Button, Drawer, Flex, Grid, Group, Stack, Title, UnstyledButton} from "@mantine/core";
import {useRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";
import {IconChevronLeft, IconX} from "@tabler/icons-react";
import SearchBox from "../../common/SearchBox";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";
import {SearchRecentHistory} from "../../../util/SearchRecentHistory";

function MobileSearchDrawer() {
    const {classes} = customStyle();
    const [opened, setOpened] = useRecoilState(searchDrawerState);
    const {handleAddKeyword, keywords} = SearchRecentHistory();

    const handleOpened = () => {
        setOpened(prev => !prev);
    }

    const items = keywords.map((item:string, idx:number) => (
        <UnstyledButton key={idx}>
            {item}
        </UnstyledButton>
    ));

    return (
        <Drawer.Root opened={opened}
                     onClose={handleOpened}
                     position={"top"}
                     size={"100%"}
                     transitionProps={{duration: 400}}>
            <Drawer.Overlay/>
            <Drawer.Content>
                <Drawer.Header>
                    <IconChevronLeft size={"3vh"}
                                     onClick={handleOpened}
                                     style={{paddingRight: "2vh"}}
                    />
                    <SearchBox/>
                </Drawer.Header>
                <Drawer.Body>
                    <Stack>
                        <Title order={4}>최근 검색 기록</Title>
                        <Group>
                            {items}
                        </Group>

                        <Title order={4}>카테고리</Title>
                        <Flex gap={"7vw"} className={classes["category-scroll"]}>
                            <MobileCategoryBtn/>
                        </Flex>
                    </Stack>
                </Drawer.Body>
            </Drawer.Content>
        </Drawer.Root>
    )
        ;
}

export default MobileSearchDrawer;