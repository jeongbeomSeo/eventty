import React, {useEffect, useState} from "react";
import {Avatar, Badge, Divider, Drawer, Flex, Group, Paper, Space, Stack, Text} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";
import {IconChevronDown} from "@tabler/icons-react";
import {DrawerContent} from "@mantine/core/lib/Drawer/DrawerContent/DrawerContent";
import EventDetailModal from "../EventDetailModal";
import {useLocation, useNavigate} from "react-router-dom";
import {CheckLogin} from "../../../util/CheckLogin";

function MobileTicketInfo({open}: { open: boolean }) {
    const [opened, setOpened] = useState(open);
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const isLoggedIn = CheckLogin();
    const [modalOpened, setModalOpened] = useState(false);

    const handleOpen = () => {
        setOpened(prev => !prev);
    }

    const onClickTicket = () => {
        if (isLoggedIn) {
            navigate("ticket", {state: pathname});
        } else {
            setModalOpened(prev => !prev);
        }
    }

    useEffect(() => {
        handleOpen();
    }, [open]);

    return (
        <>
            <EventDetailModal open={modalOpened}/>
            <Drawer.Root opened={opened}
                         onClose={handleOpen}
                         size={"60%"}
                         position={"bottom"}
                         style={{overflowY: "scroll"}}
                         zIndex={96}>
                <Drawer.Overlay opacity={0.4}/>
                <Drawer.Content>
                    <Drawer.Header style={{justifyContent: "center"}}>
                        <IconChevronDown onClick={handleOpen} size={"2rem"}/>
                    </Drawer.Header>
                    <Drawer.Body style={{paddingBottom: "15vh"}}>
                        <Stack onClick={onClickTicket}>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                            <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                        </Stack>
                    </Drawer.Body>
                </Drawer.Content>
            </Drawer.Root>
        </>
    );
}

export default MobileTicketInfo;