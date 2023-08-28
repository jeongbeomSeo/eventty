import React, {useEffect, useRef, useState} from "react";
import {Link, useLoaderData} from "react-router-dom";
import {IEvent, IEventDetail} from "../../types/IEvent";
import {
    Anchor,
    Avatar,
    Badge,
    Box,
    Button,
    Container,
    Divider,
    Grid,
    Group,
    Image, LoadingOverlay,
    Paper,
    Stack,
    Text,
    Title, useMantineTheme
} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {useRecoilValue} from "recoil";
import {userState} from "../../states/userState";
import {useMediaQuery} from "react-responsive";
import WebEventDetail from "../../components/event/web/WebEventDetail";
import MobileEventDetail from "../../components/event/mobile/MobileEventDetail";

function Detail() {
    const mobile = useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`});

    return (
        <>
            {mobile ? <MobileEventDetail/> : <WebEventDetail/>}
        </>
    );
}

export default Detail;