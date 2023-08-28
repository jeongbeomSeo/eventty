import React from "react";
import {Avatar, Button, Grid, Group, Menu, UnstyledButton} from "@mantine/core";
import {useRecoilValue} from "recoil";
import {userState} from "../../../../states/userState";
import {IconReceipt, IconSettings, IconUser} from "@tabler/icons-react";
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
                        to={"/"}
                        className={classes["btn-primary-outline"]}
                        style={{padding: "0 2rem", marginRight: "1rem"}}>
                    주최하기
                </Button>
            }
            <Menu width={"20vw"} shadow={"sm"} position={"top-end"}>
                <Menu.Target>
                    <UnstyledButton>
                        <Avatar src={""} radius={"xl"}/>
                    </UnstyledButton>
                </Menu.Target>
                <Menu.Dropdown>
                    <Menu.Item style={{pointerEvents: "none"}}>
                        <Group>
                            <Avatar src={""} radius={"xl"}/>
                            {userInfo.nickname}
                        </Group>
                    </Menu.Item>
                    <Menu.Label>내 프로필</Menu.Label>
                    <Menu.Item icon={<IconUser/>}>마이페이지</Menu.Item>
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