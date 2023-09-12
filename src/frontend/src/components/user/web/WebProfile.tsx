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

function WebProfile() {
    const {classes} = customStyle();

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
                        <TextInput label={"이름"} className={classes["input"]}/>
                        {/*<PhoneNumberInput value={phoneNumber} onInput={handlePhoneNumberChange}/>*/}
                        <BirthdayPicker label={"생년월일"} value={selectedDate} onChange={handleDateChange}/>
                        <Group position={"right"}>
                            <Button className={classes["btn-gray-outline"]}>취소</Button>
                            <Button className={classes["btn-primary"]}>저장</Button>
                        </Group>
                    </Stack>
                </Flex>

                <Title order={3}>보안</Title>
                <Divider/>
                <Group position={"apart"}>
                    <Title order={5}>비밀번호</Title>
                    <Button className={classes["btn-primary"]}>비밀번호 변경</Button>
                </Group>

                <Title order={3}>주소</Title>
                <Divider/>
                <TextInput label={"주소"} className={classes["input"]}/>
            </Stack>
        </>
    );
}

export default WebProfile;