import React from "react";
import {Avatar, Button, Divider, Grid, Group, Indicator, Menu, UnstyledButton} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {userState} from "../../../../states/userState";
import {IconBell, IconHome, IconReceipt, IconSettings, IconUser} from "@tabler/icons-react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import customStyles from "../../../../styles/customStyle";
import {useFetch} from "../../../../util/hook/useFetch";

function WebUserInfoBtn() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const userStateValue = useRecoilValue(userState);
    const {logoutFetch} = useFetch();

    const {classes} = customStyles();

    return (
        <>
            {userStateValue.isHost &&
                <Button component={Link}
                        to={"/write"}
                        className={classes["btn-primary-outline"]}
                        style={{padding: "0 2rem", marginRight: "1rem"}}>
                    주최하기
                </Button>
            }
            <Menu width={"12rem"} shadow={"sm"} position={"top-end"}>
                <Menu.Target>
                    <Avatar src={""} radius={"xl"} style={{cursor: "pointer"}}/>
                </Menu.Target>
                <Menu.Dropdown>
                    <Menu.Item style={{pointerEvents: "none"}}>
                        <Group>
                            <Avatar src={""} radius={"xl"}/>
                            {userStateValue.email}
                        </Group>
                    </Menu.Item>
                    <Divider/>

                    <Menu.Item icon={<IconUser/>} component={Link} to={"/users/profile"}>마이페이지</Menu.Item>
                    <Menu.Item icon={<IconReceipt/>}
                               component={Link}
                               to={userStateValue.isHost ? "/users/events" : "/users/reservations"}>
                        {userStateValue.isHost ? "주최 내역" : "예약 내역"}
                    </Menu.Item>
                    <Menu.Divider/>

                    <Menu.Item onClick={() => logoutFetch()}>로그아웃</Menu.Item>
                </Menu.Dropdown>
            </Menu>
        </>
    );
}

export default WebUserInfoBtn;