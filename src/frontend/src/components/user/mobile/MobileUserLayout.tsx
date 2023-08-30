import React, {useEffect, useMemo, useState} from "react";
import {Container, Stack, Tabs} from "@mantine/core";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {tab} from "@testing-library/user-event/dist/tab";
import customStyle from "../../../styles/customStyle";

function MobileUserLayout() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const {classes} = customStyle();

    const activeTab = pathname.split("/").pop();
    const handleTabClick = (value:string) => {
        navigate(`users/${value}`);
    };

    return (
        <Container>
            <Tabs value={activeTab}
                  className={classes["tabs-primary"]}
                  style={{marginTop:"1vh"}}>
                <Stack>
                    <Tabs.List>
                        <Tabs.Tab value={"profile"} onClick={() => handleTabClick("profile")}>내 정보</Tabs.Tab>
                        <Tabs.Tab value={"events"} onClick={() => handleTabClick("events")}>주최 내역</Tabs.Tab>
                        <Tabs.Tab value={"reservations"} onClick={() => handleTabClick("reservations")}>예약 내역</Tabs.Tab>
                    </Tabs.List>

                    <Tabs.Panel value={"profile"}>
                        <Outlet/>
                    </Tabs.Panel>
                </Stack>
            </Tabs>
        </Container>
    );
}

export default MobileUserLayout;