import React from "react";
import {Text, UnstyledButton} from "@mantine/core";
import {Link} from "react-router-dom";
import {
    IconBallBaseball, IconBook, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette, IconPiano, IconPresentation,
    IconTent
} from "@tabler/icons-react";
import customStyle from "../../../styles/customStyle";

function MobileCategoryBtn() {
    const {classes} = customStyle();

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

    const items = CATEGORY_LIST.map((item) => (

        <UnstyledButton component={Link}
                        to={item.link}
                        key={item.category}
                        // className={classes["mobile-nav-link"]}
                        style={{textAlign: "center"}}>
            {item.icon}
            <Text fz={"xs"}>{item.category}</Text>
        </UnstyledButton>
    ));

    return (
        <>{items}</>
    );
}

export default MobileCategoryBtn;