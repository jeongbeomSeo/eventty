import React from "react";
import {Box, Button, Container, Group, UnstyledButton} from "@mantine/core";
import {IconArrowLeft} from "@tabler/icons-react";
import customStyle from "../../styles/customStyle";
import {Link} from "react-router-dom";

interface IWriteSubmit {
    onSubmit: () => void;
}

function WriteHeader({onSubmit}: IWriteSubmit) {
    const {classes} = customStyle();

    return (
        <Box style={{
            background: "white",
            position: "sticky",
            top: 0,
            width: "100%",
            height: "4rem",
            // borderBottom: "1px solid #e6e6e6",
            zIndex: 99
        }}>
            <Container style={{height: "100%"}}>
                <Group align={"center"} style={{height: "100%"}} position={"apart"}>
                    <UnstyledButton component={Link} to={"/"}>
                        <IconArrowLeft color={"#666666"}/>
                    </UnstyledButton>

                    <Group>
                        <Button className={classes["btn-primary-outline"]}>임시저장</Button>
                        <Button onClick={onSubmit} className={classes["btn-primary"]}>게시하기</Button>
                    </Group>
                </Group>
            </Container>
        </Box>
    );
}

export default WriteHeader;