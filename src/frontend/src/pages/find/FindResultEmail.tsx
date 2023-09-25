import React, {useEffect} from "react";
import {Button, Stack, Text} from "@mantine/core";
import {useLocation, useNavigate} from "react-router-dom";
import customStyle from "../../styles/customStyle";

function FindResultEmail() {
    const {classes} = customStyle();
    const {state} = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        return(()=>{

        })
    }, []);

    return (
        <Stack>
            <Text>이메일: {state[0].email}</Text>
            <Button onClick={() => navigate("/login")}
                className={classes["btn-primary-outline"]}>돌아가기</Button>
        </Stack>
    );
}

export default FindResultEmail;