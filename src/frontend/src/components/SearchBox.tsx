import React from "react";
import {ActionIcon, TextInput} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {IconSearch} from "@tabler/icons-react";
import {Link} from "react-router-dom";

function SearchBox() {
    const {classes} = customStyle();

    return (
        <>
            <TextInput
                className={classes["search-box"]}
                size={"lg"}
                radius={"xl"}
                rightSection={
                    <ActionIcon component={Link} to={"/"} variant={"transparent"}>
                        <IconSearch/>
                    </ActionIcon>
                }
                type={"search"}
            />
        </>
    );
}

export default SearchBox;