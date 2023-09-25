import React from "react";
import {useLoaderData} from "react-router-dom";
import {IEvent, IEventUserBookings} from "../../../types/IEvent";
import MobileRegisterBtn from "./MobileRegisterBtn";
import {Stack, Title} from "@mantine/core";
import MobileApplyBtn from "./MobileApplyBtn";

function MobileUserBookings() {
    const DATA = useLoaderData() as IEventUserBookings[];

    const items = DATA.map(item => (
        <MobileApplyBtn data={item}/>
    ));

    return (
        <Stack>
            <Title order={3}>예약 내역</Title>
            {items}
        </Stack>
    );
}

export default MobileUserBookings;