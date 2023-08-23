import React, {memo} from "react";
import {Badge, Card, Group, Image, SimpleGrid, Stack, Text, Title,} from "@mantine/core";
import {Link, useLoaderData} from "react-router-dom";

const EVENT_LIST = [
    {
        article: "2023 CassCool Festival",
        date: "8월 19일",
        description: "description test wordBreak test wordBreak testwordBreak test",
        new: false,
        hot: true
    },
    {article: "[얼리버드] 일리야 밀스타인", date: "9월 20일 ~ 12월 15일", description: "description", new: true, hot: true},
    {article: "뮤지컬 ＇벤허＇", date: "9월 2일 ~ 11월 19일", description: "description", new: true, hot: false},
    {article: "뮤지컬 〈모차르트!〉", date: "6월 15일 ~ 8월 22일", description: "description", new: false, hot: true},
    {article: "[서울] 코엑스아쿠아리움 입장권(8/1~)", date: "7월 19일 ~ 8월 31일", description: "description", new: false, hot: false},
    {article: "리스펙 페스티벌 2023 - 공식티켓", date: "9월 2일 ~ 9월 3일", description: "description", new: true, hot: false},
    {article: "2023 이슬라이브 페스티벌", date: "9월 2일", description: "description", new: false, hot: true},
    {article: "뮤지컬 〈멤피스〉", date: "7월 20일 ~ 10월 22일", description: "description", new: false, hot: false},
];

function WebList() {
    const events = useLoaderData();

    const items = EVENT_LIST.map((item) => (
        <Link to={"0"} key={item.article}>
            <Card padding={"0"}>
                <Group style={{alignItems: "flex-start"}}>
                    <Image
                        src="https://source.unsplash.com/random?space"
                        height={200}
                        radius={"md"}
                        withPlaceholder
                    />
                    <Stack spacing={"0.5rem"}>
                        <Group spacing={"xs"}>
                            <Badge radius={"sm"} color={"red"}>D-00</Badge>
                            {item.hot && <Badge radius={"sm"} color={"lime"}>인기</Badge>}
                            {item.new && <Badge radius={"sm"} color={"indigo"}>신규</Badge>}
                        </Group>
                        <Text fz={"sm"} fw={700} color={"var(--primary)"}>{item.date}</Text>
                        <Title order={5}>{item.article}</Title>
                        <Text fz={"sm"} c={"gray"}>{item.description}</Text>
                    </Stack>
                </Group>
            </Card>
        </Link>
    ));

    return (
        <SimpleGrid
            cols={3}
            spacing={"md"}
            verticalSpacing={"3rem"}
        >
            {items}
        </SimpleGrid>
    );
}

export default WebList;