import React, {useEffect, useState} from "react";
import {Drawer, Stack} from "@mantine/core";
import TicketBtn from "../TicketBtn";
import {IconChevronDown} from "@tabler/icons-react";
import {useLocation, useNavigate} from "react-router-dom";
import {CheckLogin} from "../../../util/CheckLogin";
import {useModal} from "../../../util/hook/useModal";
import {IEventTicketDetail} from "../../../types/IEvent";

interface ITickets {
    open: boolean;
    tickets: IEventTicketDetail[];
}

function MobileTicketInfo({open, tickets}: ITickets) {
    const [opened, setOpened] = useState(open);

    const handleOpen = () => {
        setOpened(prev => !prev);
    }

    const items = tickets.map(item => (
        <TicketBtn key={item.id}
                   id={item.id}
                   name={item.name}
                   price={item.price}
                   quantity={item.quantity}
                   applied={item.appliedTicketCount}/>
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