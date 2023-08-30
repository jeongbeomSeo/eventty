import React from "react";
import {Button, Flex, Group, SimpleGrid, Stack, Text, UnstyledButton, useMantineTheme} from "@mantine/core";
import {Link} from "react-router-dom";
import customStyle from "../../../styles/customStyle";
import {
    IconBallBaseball, IconBook,
    IconCategoryFilled, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette, IconPiano, IconPresentation,
    IconTent
} from "@tabler/icons-react";
import {useMediaQuery} from "react-responsive";

const ICON_SIZE = "40px"

const CATEGORY_LIST = [
    {category: "콘서트", link: "", icon: <IconHandRock size={ICON_SIZE}/>},
    {category: "클래식", link: "", icon: <IconPiano size={ICON_SIZE}/>},
    {category: "전시", link: "", icon: <IconPalette size={ICON_SIZE}/>},
    {category: "스포츠", link: "", icon: <IconBallBaseball size={ICON_SIZE}/>},
    {category: "레저/캠핑", link: "", icon: <IconTent size={ICON_SIZE}/>},
    {category: "아동/가족", link: "", icon: <IconHorseToy size={ICON_SIZE}/>},
    {category: "영화", link: "", icon: <IconMovie size={ICON_SIZE}/>},
    {category: "IT", link: "", icon: <IconCode size={ICON_SIZE}/>},
    {category: "교양", link: "", icon: <IconBook size={ICON_SIZE}/>},
    {category: "TOPIC", link: "", icon: <IconPresentation size={ICON_SIZE}/>},
]

function WebCategoryBtn() {
    const {classes} = customStyle();

    const items = CATEGORY_LIST.map((item) => (

        <UnstyledButton component={Link} to={item.link} key={item.category} style={{textAlign: "center"}}>
            {item.icon}
            <Text fz={"xs"}>{item.category}</Text>
        </UnstyledButton>
    ));

    return (
        <SimpleGrid
            cols={10}
            breakpoints={[
                {maxWidth: "xs", cols: 6}
            ]}
        >
            {items}
        </SimpleGrid>
    );
}

export default WebCategoryBtn;