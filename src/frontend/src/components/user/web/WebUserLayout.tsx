import React from "react";
import {Container, Divider, Grid, Stack, Title, UnstyledButton} from "@mantine/core";
import {Link, Outlet} from "react-router-dom";
import {useRecoilValue} from "recoil";
import {userState} from "../../../states/userState";

function WebUserLayout() {
    const userStateValue = useRecoilValue(userState);

    return (
        <Container>
            <Grid style={{padding: "1rem"}}>
                <Grid.Col span={3}>
                    <Stack>
                        <Title order={3}>마이페이지</Title>
                        <Divider/>
                        <UnstyledButton component={Link} to={"users/profile"}>내 정보</UnstyledButton>
                        {userStateValue.isHost ?
                            <UnstyledButton component={Link} to={"users/events"}>주최 내역</UnstyledButton> :
                            <UnstyledButton component={Link} to={"users/reservations"}>예약 내역</UnstyledButton>
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