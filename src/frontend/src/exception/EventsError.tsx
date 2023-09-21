import React from "react";
import {Flex, Title} from "@mantine/core";

function EventsError() {
    return (
        <Flex justify={"center"}
              align={"center"}
              style={{width: "100p%", height: "50vh"}}>
            <Title order={2}>검색 결과가 없습니다</Title>
        </Flex>
    );
}

export default EventsError;