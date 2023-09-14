import React, {useState} from "react";
import {Box, Container, Divider, Flex, Grid, Group, Stack, Text, Title, UnstyledButton} from "@mantine/core";
import {Link, Outlet} from "react-router-dom";
import {useRecoilValue} from "recoil";
import {userState} from "../../states/userState";
import {set} from "react-hook-form";

const MENU_LIST = [
    {link: "users/profile", value: "내 정보"},
    {link: "users/events", value: "주최 내역"},
    {link: "users/reservations", value: "예약 내역"},
]

function WebUserLayout() {
    const userStateValue = useRecoilValue(userState);
    const [profile, setProfile] = useState(true);

    return (
        <Container>
            <Grid style={{padding: "1rem"}}>
                <Grid.Col span={3}>
                    <Stack>
                        <Title order={3}>마이페이지</Title>
                        <Divider/>
                        <Flex gap={"0.5rem"}>
                            <Box style={{
                                height: "auto",
                                width: "5px",
                                background: "var(--primary)",
                                borderRadius: "10rem",
                                visibility: profile ? "inherit" : "hidden",
                            }}/>
                            <UnstyledButton component={Link}
                                            to={"users/profile"}
                                            onClick={() => setProfile(true)}
                                            style={{
                                                background: profile ? "#eeeeee" : "",
                                                borderRadius: "0.3rem",
                                                padding: "0.7rem",
                                                width: "100%",
                                            }}>
                                내 정보
                            </UnstyledButton>
                        </Flex>
                        {userStateValue.isHost ?
                            <Flex gap={"0.5rem"}>
                                <Box style={{
                                    height: "auto",
                                    width: "5px",
                                    background: "var(--primary)",
                                    borderRadius: "10rem",
                                    visibility: !profile ? "inherit" : "hidden",
                                }}/>
                                <UnstyledButton component={Link}
                                                to={"users/events"}
                                                onClick={() => setProfile(false)}
                                                style={{
                                                    background: !profile ? "#eeeeee" : "",
                                                    borderRadius: "0.3rem",
                                                    padding: "0.7rem",
                                                    width: "100%",
                                                }}>
                                    주최 내역
                                </UnstyledButton>
                            </Flex> :
                            <Flex gap={"0.5rem"}>
                                <Box style={{
                                    height: "auto",
                                    width: "5px",
                                    background: "var(--primary)",
                                    borderRadius: "10rem",
                                    visibility: !profile ? "inherit" : "hidden",
                                }}/>
                                <UnstyledButton component={Link}
                                                to={"users/reservations"}
                                                onClick={() => setProfile(false)}
                                                style={{
                                                    background: !profile ? "#eeeeee" : "",
                                                    borderRadius: "0.3rem",
                                                    padding: "0.7rem",
                                                    width: "100%",
                                                }}>
                                    예약 내역
                                </UnstyledButton>
                            </Flex>
                        }
                    </Stack>
                </Grid.Col>
                <Grid.Col span={9}>
                    <Outlet/>
                </Grid.Col>
            </Grid>
        </Container>
    );
}

export default WebUserLayout;