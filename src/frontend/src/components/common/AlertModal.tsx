import React, {useEffect, useState} from "react";
import {Modal} from "@mantine/core";

function AlertModal({open, children}: { open: boolean, children: React.ReactNode }) {
    const [opened, setOpened] = useState(open);

    console.log(opened);

    const handleOpen = () => {
        setOpened(prev => !prev);
    }

    useEffect(() => {
        handleOpen()
    }, [open]);

    return (
        <Modal opened={opened} onClose={handleOpen} centered>
            {children}
        </Modal>
    );
}

export default AlertModal;