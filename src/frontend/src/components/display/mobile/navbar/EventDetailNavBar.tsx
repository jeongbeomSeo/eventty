import React, {useState} from "react";
import {Button, Container, Flex, Group, UnstyledButton} from "@mantine/core";
import {IconBell, IconHeart,} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import MobileTicketInfo from "../../../event/mobile/MobileTicketInfo";
import {useRecoilState, useSetRecoilState} from "recoil";
import {eventTicketDrawerState} from "../../../../states/eventTicketDrawerState";

function EventDetailNavBar() {
    const [eventTIcketDrawer, setEventTicketDrawer] = useRecoilState(eventTicketDrawerState);
    const {classes} = customStyle();

    return (
        <Container style={{width: "100%", height: "100%"}}>
            <Flex align={"center"} style={{height: "100%"}}>
                <Button className={classes[`${eventTIcketDrawer ? "btn-primary-outline" : "btn-primary"}`]}
                        style={{width: "100%", height: "80%"}}
                        onClick={() => setEventTicketDrawer(prev => !prev)}>예약하기</Button>
            </Flex>
        </Container>
    );
}

export default EventDetailNavBar;