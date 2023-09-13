import React, {useState} from "react";
import {Button, Container, Group, UnstyledButton} from "@mantine/core";
import {IconBell, IconHeart,} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import MobileTicketInfo from "../../../event/mobile/MobileTicketInfo";
import {useRecoilState, useSetRecoilState} from "recoil";
import {eventTicketDrawerState} from "../../../../states/eventTicketDrawerState";

function EventDetailNavBar() {
    const setEventTicketDrawerValue = useSetRecoilState(eventTicketDrawerState);
    const {classes} = customStyle();

    return (
        <Container style={{width: "100%", height: "100%"}}>
            <Group position={"apart"} noWrap style={{height: "100%"}}>
                <UnstyledButton style={{height: "80%", textAlign: "center"}}
                                className={classes["mobile-nav-link"]}>
                    <IconHeart/>
                    info
                </UnstyledButton>
                <UnstyledButton style={{height: "80%", textAlign: "center"}}
                                className={classes["mobile-nav-link"]}>
                    <IconBell/>
                    info
                </UnstyledButton>
                <Button className={classes["btn-primary"]} style={{width: "100%", height: "80%"}}
                        onClick={() => setEventTicketDrawerValue(prev => !prev)}>예약하기</Button>
            </Group>
        </Container>
    );
}

export default EventDetailNavBar;