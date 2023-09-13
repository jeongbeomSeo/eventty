import React, {JSX, useEffect, useState} from "react";
import {Stack, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import SearchBox from "../../common/SearchBox";
import {SearchRecentHistory} from "../../../util/SearchRecentHistory";
import SearchKeywordsItem from "../SearchKeywordsItem";

function WebSearchKeywordsList() {
    const {classes} = customStyle();
    const {keywords, handleAddKeyword, handleDeleteKeyword} = SearchRecentHistory();
    const [items, setItems] = useState<React.ReactNode[] | null>(null);

    useEffect(() => {
        const mappedItems = keywords.map((item: string, idx: number) => (
            <SearchKeywordsItem key={idx} item={item} onClick={handleAddKeyword} onDelete={handleDeleteKeyword}/>
        ));

        setItems(mappedItems);
    }, [keywords]);

    return (
        <Stack style={{width: "80vw", minHeight: "50vh"}}>
            <SearchBox onAddKeyword={handleAddKeyword}/>
            <Title order={6}>최근 검색어</Title>
            <Stack>
                {items}
            </Stack>
        </Stack>
    );
}

export default WebSearchKeywordsList;