import React from "react";
import {Badge, Card, Container, Group, Image, SimpleGrid, Stack, Text, Title} from "@mantine/core";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent} from "../../types/IEvent";

function EventsCategory() {
    const EVENT_CATEGORY_LIST = useLoaderData() as IEvent[];

    return (
        <>
            <Title>[ 카테고리명 ]</Title>
            <SimpleGrid
                cols={3}
                spacing={"md"}
                verticalSpacing={"3rem"}
            >
                {/*{items}*/}
            </SimpleGrid>
        </>
    );
}

export default EventsCategory;