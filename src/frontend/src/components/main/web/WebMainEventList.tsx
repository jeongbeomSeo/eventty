import React from "react";
import {AspectRatio, Image, Paper, SimpleGrid, Stack, Text, Title} from "@mantine/core";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import WebEventItem from "../../event/web/WebEventItem";

function WebMainEventList({data}: { data: IEvent[] }) {

    const items = data.map((item) => {
        if (item.isDeleted) return;

        return (
            <WebEventItem item={item} badge={false}/>
        )
    });

    return (
        <SimpleGrid cols={5}
                    breakpoints={[{maxWidth: "62rem", cols: 3}]}
                    verticalSpacing={"2rem"}>
            {items}
        </SimpleGrid>
    );
}

export default WebMainEventList;