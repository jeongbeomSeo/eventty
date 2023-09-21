import React from "react";
import {Box, Divider, Group, Stack, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import WebCategoryBtn from "./WebCategoryBtn";
import {Outlet, useParams, useSearchParams} from "react-router-dom";
import EventsList from "../../../pages/events/EventsList";
import SearchResult from "../SearchResult";

function WebEvents() {
    const {category} = useParams();
    const [searchParams, setSearchParams] = useSearchParams();

    return (
        <>
            {category &&
                <>
                    <Title>{category}</Title>
                    <Divider my={"1rem"}/>
                </>}
            {searchParams.size > 0 && <SearchResult/>}
            <Outlet/>
        </>
    );
}

export default WebEvents;