import React from "react";
import {IEvent} from "../../../types/IEvent";
import {Link, useNavigate} from "react-router-dom";
import {Badge, Card, Grid, Group, Image, Paper, SimpleGrid, Stack, Text, Title} from "@mantine/core";
import MobileEventItem from "../../event/mobile/MobileEventItem";

function MobileMainEventList({data}: { data: IEvent[] }) {
    const navigate = useNavigate();

    const items = data.map((item) => {
        if (item.isDeleted) return;

        return (
            <MobileEventItem item={item} badge={false}/>
        )
    });

    return (
        <SimpleGrid cols={1}>
            {items}
        </SimpleGrid>
    );
}

export default MobileMainEventList;