import {Container, Header, Text, UnstyledButton,} from "@mantine/core";
import {Link, useNavigate, useNavigation} from "react-router-dom";
import Logo from "../../../common/Logo";
import customStyle from "../../../../styles/customStyle";
import React from "react";
import {IconChevronLeft, IconHome} from "@tabler/icons-react";

const HEADER_HEIGHT = "65px";

function EventDetailHeader() {
    const navigate = useNavigate();

    const {classes} = customStyle();

    return (
        <>
            <UnstyledButton onClick={() => navigate(-1)}>
                <IconChevronLeft/>
            </UnstyledButton>
            <UnstyledButton component={Link} to={"/"}>
                <IconHome/>
            </UnstyledButton>
        </>
    )
}

export default EventDetailHeader;