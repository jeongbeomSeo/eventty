import React from "react";
import {Avatar, Button, Divider, Grid, Group, Indicator, Menu, UnstyledButton} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {userState} from "../../../../states/userState";
import {IconBell, IconHome, IconReceipt, IconSettings, IconUser} from "@tabler/icons-react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import customStyles from "../../../../styles/customStyle";

function WebUserInfoBtn() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const userInfo = useRecoilValue(userState);
    const userStateValue = useRecoilValue(userState);

    const {classes} = customStyles();

    return (
        <Group>
            {userStateValue.isHost &&
                <Button component={Link}
                        to={"/write/event"}
                        className={classes["btn-primary-outline"]}
                        style={{padding: "0 2rem", marginRight: "1rem"}}>
                    주최하기
                </Button>
            }
            <Menu width={"12rem"} shadow={"sm"} position={"top-end"}>
                <Menu.Target>
                    <Indicator color={"red"} offset={5} inline withBorder disabled={false}>
                        <Avatar src={""} radius={"xl"} style={{cursor:"pointer"}}/>
                    </Indicator>
                </Menu.Target>
                <Menu.Dropdown>
                    <Menu.Item style={{pointerEvents: "none"}}>
                        <Group>
                            <Avatar src={""} radius={"xl"}/>
                            {userInfo.nickname}
                        </Group>
                    </Menu.Item>
                    <Divider/>

                    <Menu.Item icon={<IconHome/>} component={Link} to={"/"}>홈</Menu.Item>
                    <Menu.Item icon={<IconBell/>}>알림</Menu.Item>
                    <Menu.Item icon={<IconUser/>} component={Link} to={"/users/profile"}>마이페이지</Menu.Item>
                    <Menu.Item icon={<IconReceipt/>}>예약 내역</Menu.Item>
                    <Menu.Item icon={<IconSettings/>}>설정</Menu.Item>

                    <Menu.Divider/>

                    <Menu.Item onClick={() => navigate("/logout", {state: pathname})}>로그아웃</Menu.Item>
                </Menu.Dropdown>
            </Menu>
        </Group>
    );
}

export default WebUserInfoBtn;