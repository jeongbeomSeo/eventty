import React from "react";
import {Button, Drawer, Flex, Group, Stack, Title} from "@mantine/core";
import {useRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";
import {IconChevronLeft, IconX} from "@tabler/icons-react";
import SearchBox from "../../common/SearchBox";
import CategoryBtn from "../../event/CategoryBtn";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";

function MobileSearchDrawer() {
    const {classes} = customStyle();
    const [opened, setOpened] = useRecoilState(searchDrawerState);
    const handleOpened = () => {
        setOpened(prev => !prev);
    }

    const LASTEST_HISTORY = [
        {value: "AAAAAAA"},
        {value: "BB"},
        {value: "CCDDDD"},
        {value: "EEE!2"},
    ];

    const items = LASTEST_HISTORY.map((item) => (
        <Button rightIcon={<IconX/>}
                radius={"xl"}
                className={classes["btn-primary-outline"]}>
            {item.value}
        </Button>
    ))

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
                                     style={{paddingRight: "5vw"}}/>
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
    );
}

export default MobileSearchDrawer;