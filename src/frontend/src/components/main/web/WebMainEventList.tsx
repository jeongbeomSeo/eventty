import React from "react";
import {AspectRatio, Image, SimpleGrid, Stack, Text, Title} from "@mantine/core";
import {Link} from "react-router-dom";

const items = () => {
    const list = [];
    for (let i = 0; i < 10; i++) {
        list.push(
            <Link to={"/"} key={i}>
                <Stack>
                    <AspectRatio ratio={4 / 3} mih={100}>
                        <Image
                            src={null}
                            withPlaceholder
                        />
                    </AspectRatio>
                    <Stack spacing={"0.5rem"}>
                        <Text fz={"sm"} fw={700} color={"var(--primary)"}>
                            00월 00일
                        </Text>
                        <Title order={5}>[행사 제목]</Title>
                    </Stack>
                </Stack>
            </Link>
        )
    }
    return list;
}

function WebMainEventList() {
    return (
        <SimpleGrid cols={5} verticalSpacing={"2rem"}>
            {items()}
        </SimpleGrid>
    );
}

export default WebMainEventList;