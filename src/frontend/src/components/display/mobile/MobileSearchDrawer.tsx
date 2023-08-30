import React, {useState} from "react";
import {Button, Drawer, Flex, Grid, Group, Stack, Title} from "@mantine/core";
import {useRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";
import {IconChevronLeft, IconX} from "@tabler/icons-react";
import SearchBox from "../../common/SearchBox";
import WebCategoryBtn from "../../event/web/WebCategoryBtn";
import MobileCategoryBtn from "../../event/mobile/MobileCategoryBtn";
import customStyle from "../../../styles/customStyle";

function MobileSearchDrawer() {
    const {classes} = customStyle();
    const [opened, setOpened] = useRecoilState(searchDrawerState);

    const [lastestHistory, setLastestHistory] = useState([
        {key: 1, value: "AAAAAAA"},
        {key: 2, value: "BB"},
        {key: 3, value: "CCDDDD"},
        {key: 4, value: "EEE!2"},
    ])

    const handleOpened = () => {
        setOpened(prev => !prev);
    }

    const items = lastestHistory.map((item) => (
        <Button key={item.key}
                rightIcon={<IconX/>}
                radius={"xl"}
                className={classes["btn-primary-outline"]}
                onClick={() => deleteHistory(item.key)}
        >
            {item.value}
        </Button>
    ))

    const deleteHistory = (key: number) => {
        setLastestHistory(() =>
            lastestHistory.filter((item) => item.key !== key)
        );
    }

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