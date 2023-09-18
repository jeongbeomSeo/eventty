import React, {useEffect} from "react";
import {Badge, Card, Group, Image, SimpleGrid, Stack, Text, Title,} from "@mantine/core";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent} from "../../../types/IEvent";
import {getCategoryEvents, getEvents} from "../../../service/event/fetchEvent";

function WebEventList() {
    const EVENT_LIST = useLoaderData() as IEvent[];

    const items = React.useMemo(() => {
        return (EVENT_LIST?.map((item) => {
            const startAt = new Date(item.eventStartAt);
            const endtAt = new Date(item.eventEndAt);

            return (
                <Link to={`/event/${item.id}`} key={item.id}>
                    <Card padding={"0"}>
                        <Group style={{alignItems: "flex-start"}}>
                            <Image
                                src={item.image}
                                height={200}
                                radius={"md"}
                                withPlaceholder
                            />
                            <Stack spacing={"0.5rem"}>
                                <Group spacing={"xs"}>
                                    <Badge radius={"sm"} color={"red"}>D-00</Badge>
                                    <Badge radius={"sm"} color={"lime"}>인기</Badge>
                                    <Badge radius={"sm"} color={"indigo"}>신규</Badge>
                                </Group>
                                <Text fz={"sm"} fw={700} color={"var(--primary)"}>
                                    {`${startAt.getMonth() + 1}월 ${startAt.getDate()}일`}
                                    {!((startAt.getMonth() === endtAt.getMonth()) && (startAt.getDate() === endtAt.getDate())) &&
                                        ` ~ ${endtAt.getMonth() + 1}월 ${endtAt.getDate()}일`}
                                </Text>
                                <Title order={5}>{item.title}</Title>
                                <Text fz={"sm"} c={"gray"}>{item.location}</Text>
                            </Stack>
                        </Group>
                    </Card>
                </Link>
            );
        }));
    }, [EVENT_LIST]);

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

export default React.memo(WebEventList);