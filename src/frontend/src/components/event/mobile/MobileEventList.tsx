import React from "react";
import {
    Badge, Box,
    Card, CardSection,
    Container, Divider, Flex, Grid,
    Group,
    Image,
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

function MobileEventList() {
    const navigate = useNavigate();
    const {pathname} = useLocation();

    const EVENT_LIST = useLoaderData() as IEvent[];
    const items = EVENT_LIST?.map((item) => {
        const startAt = new Date(item.eventStartAt);
        const endtAt = new Date(item.eventEndAt);

        return (
            <Card padding={"0"} radius={"md"} key={item.id}
                  onClick={() => navigate(`${item.id}`, {state: pathname})}>
                <Group noWrap style={{alignItems: "flex-start"}}
                >
                    <Image
                        src={item.image}
                        height={110}
                        width={150}
                        radius={"md"}
                        withPlaceholder
                    />
                    <Stack spacing={"0.25rem"}>
                        <Group spacing={"xs"}>
                            <Badge radius={"sm"} color={"red"} size={"xs"}>D-00</Badge>
                            <Badge radius={"sm"} color={"lime"}>인기</Badge>
                            <Badge radius={"sm"} color={"indigo"}>신규</Badge>
                        </Group>
                        <Text fz={"xs"} fw={700} color={"var(--primary)"}>
                            {`${startAt.getMonth() + 1}월 ${startAt.getDate()}일`}
                            {startAt !== endtAt && ` ~ ${endtAt.getMonth() + 1}월 ${endtAt.getDate()}일`}
                        </Text>
                        <Title order={6}>{item.title}</Title>
                        <Text fz={"xs"} c={"gray"}>{item.location}</Text>
                    </Stack>
                </Group>
            </Card>
        )
    });

    return (
        <SimpleGrid
            cols={1}
            verticalSpacing={"md"}
        >
            {items}
        </SimpleGrid>
    );
}

export default MobileEventList;