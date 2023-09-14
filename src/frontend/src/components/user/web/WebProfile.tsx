import React, {ChangeEvent, useState} from "react";
import {
    Avatar,
    Button,
    Divider,
    FileButton,
    Flex,
    Group,
    Indicator,
    Overlay,
    Stack, Text,
    TextInput,
    Title
} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import BirthdayPicker from "../../common/BirthdayPicker";
import PhoneNumberInput from "../../common/PhoneNumberInput";
import {Link, useLoaderData} from "react-router-dom";
import {IUser} from "../../../types/IUser";

function WebProfile() {
    const {classes} = customStyle();
    const DATA = useLoaderData() as IUser;

    const [phoneNumber, setPhoneNumber] = useState("");
    const [selectedDate, setSelectedDate] = useState<Date>();

    const handlePhoneNumberChange = (formattedValue: string) => {
        setPhoneNumber(formattedValue);
    }
    const handleDateChange = (newDate: Date) => {
        setSelectedDate(newDate);
    }

    return (
        <>
            <Stack spacing={"3rem"}>
                <Stack>
                    <Title order={3}>프로필</Title>
                    <Divider/>
                    <Flex gap={"2rem"}>
                        <Stack align={"center"}>
                            <Avatar size={"8rem"} radius={"8rem"}/>
                            <Button className={classes["btn-primary"]}>이미지 변경</Button>
                        </Stack>

                        <Stack style={{width: "100%"}}>
                            <TextInput label={"이메일"} disabled className={classes["input"]}/>
                            <TextInput label={"이름"} defaultValue={DATA.name} className={classes["input"]}/>
                            <TextInput label={"휴대폰 번호"} defaultValue={DATA.phone} className={classes["input"]}/>
                            <BirthdayPicker label={"생년월일"} value={selectedDate} onChange={handleDateChange}/>
                            <TextInput label={"주소"} className={classes["input"]}/>
                            <Group position={"right"}>
                                <Button className={classes["btn-gray-outline"]}>취소</Button>
                                <Button className={classes["btn-primary"]}>저장</Button>
                            </Group>
                        </Stack>
                    </Flex>
                </Stack>

                <Stack>
                    <Title order={3}>보안</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>비밀번호</Title>
                        <Button className={classes["btn-primary"]}>비밀번호 변경</Button>
                    </Group>
                </Stack>

                <Stack>
                    <Title order={3} color={"red"}>회원 탈퇴</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>회원 탈퇴</Title>
                        <Button component={Link}
                                to={"/delete-account"}
                                color={"red"}>회원 탈퇴</Button>
                    </Group>
                </Stack>
            </Stack>
        </>
    );
}

export default WebProfile;