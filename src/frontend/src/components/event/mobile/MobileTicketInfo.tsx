import React, {useEffect, useState} from "react";
import {Avatar, Badge, Divider, Drawer, Flex, Group, Paper, Space, Stack, Text} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import TicketBtn from "../TicketBtn";
import {IconChevronDown} from "@tabler/icons-react";
import {DrawerContent} from "@mantine/core/lib/Drawer/DrawerContent/DrawerContent";
import {useLocation, useNavigate} from "react-router-dom";
import {CheckLogin} from "../../../util/CheckLogin";
import {useModal} from "../../../util/hook/useModal";
import {IEventTicket, IEventTicketDetail} from "../../../types/IEvent";

interface ITickets {
    open: boolean;
    tickets: IEventTicketDetail[];
}

function MobileTicketInfo({open, tickets}:ITickets) {
    const [opened, setOpened] = useState(open);
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const isLoggedIn = CheckLogin();
    const {loginAlertModal} = useModal();

    const handleOpen = () => {
        setOpened(prev => !prev);
    }

    const onClickTicket = () => {
        if (isLoggedIn) {
            navigate("/ticket", {state: pathname});
        } else {
            loginAlertModal();
        }
    }

    const items = tickets.map(item => (
        <TicketBtn key={item.id}
                   name={item.name}
                   price={item.price}
                   quantity={item.quantity}
                   onClick={onClickTicket}/>
    ));

    useEffect(() => {
        handleOpen();
    }, [open]);

    return (
        <>
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
                        <Stack>
                            {items}
                        </Stack>
                    </Drawer.Body>
                </Drawer.Content>
            </Drawer.Root>
        </>
    );
}

export default MobileTicketInfo;