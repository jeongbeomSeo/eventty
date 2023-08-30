import React, {useEffect, useState} from "react";
import {Button, Flex, Modal} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {CheckLogin} from "../../util/CheckLogin";
import {useLocation, useNavigate} from "react-router-dom";

function EventDetailModal({open}: { open: boolean }) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const [modalOpened, setModalOpened] = useState(open);

    const onModalClosed = () => {
        navigate("/login", {state: pathname});
    }

    useEffect(() => {
        setModalOpened(prev => !prev);
    }, [open]);

    return (
        <Modal opened={modalOpened}
               onClose={onModalClosed}
               withCloseButton={false}
               centered
               size={"auto"}
               xOffset={""}>
            <Flex direction={"column"} align={"center"} gap={"2rem"} style={{padding: "2rem 3rem"}}>
                로그인 후 이용해주세요
                <Button onClick={onModalClosed} className={classes["btn-primary"]}>확인</Button>
            </Flex>
        </Modal>
    );
}

export default EventDetailModal;