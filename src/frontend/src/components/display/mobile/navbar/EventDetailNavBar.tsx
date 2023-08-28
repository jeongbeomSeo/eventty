import React, {useState} from "react";
import {Button, Container, Group, UnstyledButton} from "@mantine/core";
import {IconBell, IconHeart,} from "@tabler/icons-react";
import customStyle from "../../../../styles/customStyle";
import MobileTicketInfo from "../../../event/mobile/MobileTicketInfo";

function EventDetailNavBar() {
    const [openTicketInfo, setOpenTicketInfo] = useState(false);
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
                <MobileTicketInfo open={openTicketInfo}/>
                <Button className={classes["btn-primary"]} style={{width: "100%", height: "80%"}}
                        onClick={() => setOpenTicketInfo(prev => !prev)}>예약하기</Button>
            </Group>
        </Container>
    );
}

export default EventDetailNavBar;