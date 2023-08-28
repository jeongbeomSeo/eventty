import React, {useEffect, useState} from "react";
import {Avatar, Badge, Divider, Drawer, Group, Paper, Space, Stack, Text} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";

function MobileTicketInfo({open}: { open: boolean }) {
    const [opened, setOpened] = useState(open);

    const handleOpen = () => {
        setOpened(prev => !prev);
    }

    useEffect(() => {
        handleOpen();
    }, [open]);

    return (
        <Drawer opened={opened}
                onClose={handleOpen}
                withCloseButton={false}
                size={"60%"}
                position={"bottom"}
                overlayProps={{opacity: 0.4}}
                style={{overflowY: "scroll"}}
                zIndex={96}>
            <Space h={"3vh"}/>
            <Stack>
                <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
                <TicketBtn title={"제목"} content={"내용"} price={99999999} left={1234}/>
            </Stack>
            <Space h={"10vh"}/>
        </Drawer>
    );
}

export default MobileTicketInfo;