import {modals} from "@mantine/modals";
import React from "react";
import {Button, Stack, TextInput, Title} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useLocation, useNavigate} from "react-router-dom";
import {IconSearch} from "@tabler/icons-react";
import WebSearchForm from "../../components/display/web/header/WebSearchForm";

export function useModal() {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {pathname} = useLocation();

    const messageModal = (children: React.ReactNode) => {
        return modals.open({
            children: (
                <Stack align={"center"} style={{padding: "1.5rem"}}>
                    {children}
                    <Button onClick={() => modals.closeAll()}
                            className={classes["btn-primary"]}>확인</Button>
                </Stack>
            ),
            withCloseButton: false,
            centered: true,
            xOffset: "",
            size: "xs",
        })
    }

    const loginAlertModal = () => {
        return modals.open({
            children: (
                <Stack align={"center"} style={{padding: "1.5rem"}}>
                    <>로그인 후 이용해주세요</>
                    <Button onClick={() => {
                        modals.closeAll()
                        navigate("/login", {state: pathname})
                    }}
                            className={classes["btn-primary"]}>확인</Button>
                </Stack>
            ),
            withCloseButton: false,
            centered: true,
            xOffset: "",
            size: "xs",
        })
    }

    const searchModal = () => {
        return modals.open({
            children: (
                <WebSearchForm/>
            ),
            withCloseButton: false,
            xOffset: "",
            size: "auto",
            overlayProps: {opacity: 0.5},
            transitionProps: {transition: "slide-down"}
        })
    }

    return {messageModal, loginAlertModal, searchModal};
}