import React from "react";
import {Stack, Title} from "@mantine/core";
import MobileEventsDetailBtn from "./MobileEventsDetailBtn";

function MobileEventsInfo() {
    return (
        <Stack>
            <Title order={3}>주최 내역</Title>
            <MobileEventsDetailBtn id={"1"} title={"[행사 제목]"} state={0} date={new Date()}/>
            <MobileEventsDetailBtn id={"2"} title={"[행사 제목]"} state={1} date={new Date()}/>
            <MobileEventsDetailBtn id={"3"} title={"[행사 제목]"} state={2} date={new Date()}/>
        </Stack>
    );
}

export default MobileEventsInfo;