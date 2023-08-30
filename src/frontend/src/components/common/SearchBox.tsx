import React, {useState} from "react";
import {ActionIcon, TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {IconSearch} from "@tabler/icons-react";
import {Link} from "react-router-dom";

function SearchBox() {
    const {classes} = customStyle();
    const [keyword, setKeyword] = useState("");

    const onSubmit = () => {
        keyword && alert(keyword);
    }

    return (
        <TextInput className={classes["search-box"]}
                   size={"md"}
                   radius={"sm"}
                   rightSection={
                       <ActionIcon component={Link} to={"/"} variant={"transparent"}>
                           <IconSearch onClick={onSubmit}/>
                       </ActionIcon>}
                   value={keyword}
                   onChange={(e) => setKeyword(e.target.value)}
                   onKeyDown={(e) => (e.key === "Enter") && onSubmit()}
        />
    );
}

export default SearchBox;