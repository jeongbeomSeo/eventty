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
import {Link, useLoaderData} from "react-router-dom";
import SearchBox from "../../components/common/SearchBox";
import {useMediaQuery} from "react-responsive";
import {IEvent} from "../../types/IEvent";

function MobileList() {
    const EVENT_LIST = useLoaderData() as IEvent[];

    const items = EVENT_LIST?.map((item) => {
        const startAt = new Date(item.eventStartAt);
        const endtAt = new Date(item.eventEndAt);

        return (
            <Link to={`${item.id}`} key={item.id}>
                <Card padding={"0"} radius={"md"}>
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
                                {`${startAt.getMonth()+1}월 ${startAt.getDate()}일`}
                                {startAt !== endtAt && ` ~ ${endtAt.getMonth()+1}월 ${endtAt.getDate()}일`}
                            </Text>
                            <Title order={6}>{item.title}</Title>
                            <Text fz={"xs"} c={"gray"}>{item.location}</Text>
                        </Stack>
                    </Group>
                </Card>
            </Link>
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

export default MobileList;