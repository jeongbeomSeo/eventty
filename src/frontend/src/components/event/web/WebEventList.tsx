import React from "react";
import {Badge, Card, Group, Image, Paper, SimpleGrid, Stack, Text, Title,} from "@mantine/core";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import {Base64toSrc} from "../../../util/ConvertFile";
import SearchError from "../../../exception/EventsError";
import EventsError from "../../../exception/EventsError";
import WebEventItem from "./WebEventItem";

function WebEventList() {
    const DATA = useLoaderData() as IEvent[];

    const items = DATA?.map((item) => {
        if (item.isDeleted) return;

        return (
            <WebEventItem item={item} badge={true}/>
        );
    });

    return (
        <SimpleGrid
            cols={3}
            spacing={"md"}
            verticalSpacing={"3rem"}
            style={{padding: "5vh 0"}}
        >
            {items}
        </SimpleGrid>
    );
}

export default WebEventList;