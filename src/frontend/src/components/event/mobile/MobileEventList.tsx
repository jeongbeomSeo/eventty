import React from "react";
import {
    Badge, Box,
    Card, CardSection,
    Container, Divider, Flex, Grid,
    Group,
    Image, Paper,
    SimpleGrid,
    Stack,
    Text,
    Title,
    useMantineTheme
} from "@mantine/core";
import {Link, useLoaderData, useLocation, useNavigate} from "react-router-dom";
import SearchBox from "../../common/SearchBox";
import {useMediaQuery} from "react-responsive";
import {IEvent} from "../../../types/IEvent";
import {Base64toSrc} from "../../../util/ConvertFile";
import SearchError from "../../../exception/EventsError";
import EventsError from "../../../exception/EventsError";
import MobileEventItem from "./MobileEventItem";

function MobileEventList() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const DATA = useLoaderData() as IEvent[];

    if (DATA.length < 1) {
        return (
            <EventsError/>
        );
    }

    const items = DATA?.map((item, idx) => {
        if (item.isDeleted) return;

        return (
            <MobileEventItem key={idx} item={item} badge={true}/>
        )
    });

    return (
        <SimpleGrid
            cols={1}
            verticalSpacing={"md"}
            style={{paddingBottom: "10vh"}}>
            {items}
        </SimpleGrid>
    );
}

export default MobileEventList;