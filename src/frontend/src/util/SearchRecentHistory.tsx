import React, {useEffect, useState} from "react";

export const SearchRecentHistory = () => {
    const [keywords, setKeywords] = useState<string[]>(JSON.parse(localStorage.getItem("EVENTTY_RECENT_HISTORY") || "[]"));

    useEffect(() => {
        localStorage.setItem("EVENTTY_RECENT_HISTORY", JSON.stringify(keywords.slice(0, 5)));
    }, [keywords]);

    const handleAddKeyword = (keyword: string) => {
        setKeywords([keyword, ...keywords.filter(item => item !== keyword)]);
    }

    const handleDeleteKeyword = (keyword: string) => {
        setKeywords([...keywords.filter(item => item !== keyword)]);
    }

    return {keywords, handleAddKeyword, handleDeleteKeyword};
}