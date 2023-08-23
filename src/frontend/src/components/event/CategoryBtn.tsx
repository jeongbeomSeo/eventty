import React from "react";
import {Button, Group, Stack} from "@mantine/core";
import {Link} from "react-router-dom";
import customStyle from "../../styles/customStyle";
import {IconCategoryFilled} from "@tabler/icons-react";

const CATEGORY_LIST = [
    {category: "카", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "테", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "고", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "리", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "버", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "튼", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "테", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "스", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
    {category: "트", link: "", icon: <IconCategoryFilled size={"3rem"}/>},
]

function CategoryBtn() {
    const {classes} = customStyle();

    const items = CATEGORY_LIST.map((item) => (

        <Link to={item.link}>
            <Stack align={"center"}>
            {item.icon}
            {item.category}
            </Stack>
        </Link>
    ));

    return (
        <Group>
            {items}
        </Group>
    );
}

export default CategoryBtn;