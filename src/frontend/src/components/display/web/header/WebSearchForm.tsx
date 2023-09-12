import React, {JSX, useEffect, useState} from "react";
import {Stack, TextInput, Title, UnstyledButton} from "@mantine/core";
import {IconSearch, IconX} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import SearchBox from "../../../common/SearchBox";
import {Link} from "react-router-dom";
import {SearchRecentHistory} from "../../../../util/SearchRecentHistory";

function WebSearchForm() {
    const {classes} = customStyle();
    const {keywords, handleDeleteKeyword} = SearchRecentHistory();
    const [items, setItems] = useState<React.ReactNode[] | null>(null);

    useEffect(() => {
        const mappedItems = keywords.map((item: string, idx: number) => (
            <UnstyledButton key={idx}>
                {item}
                <IconX onClick={() => handleDeleteKeyword(item)}/>
            </UnstyledButton>
        ));

        setItems(mappedItems);
    }, [keywords]);

    console.log(items);

    return (
        <Stack style={{width: "80vw", minHeight: "50vh"}}>
            <SearchBox/>
            <Title order={6}>최근 검색어</Title>
            <Stack>
                {items}
            </Stack>
        </Stack>
    );
}

export default WebSearchForm;