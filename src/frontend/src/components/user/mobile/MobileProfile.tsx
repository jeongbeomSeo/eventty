import React, {ChangeEvent, useState} from "react";
import {Avatar, Button, Center, Flex, Group, Paper, Stack, Text, TextInput, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {DatePickerInput} from "@mantine/dates";
import BirthdayPicker from "../../common/BirthdayPicker";

function PaperItem({children}: { children: React.ReactNode }) {
    return (
        <Paper radius={"md"} p={"2rem"} style={{border: "1px solid #cdcdcd"}}>
            <Stack>
                {children}
            </Stack>
        </Paper>
    )
}

function MobileProfile() {
    const {classes} = customStyle();

    const [phoneNumber, setPhoneNumber] = useState("");
    const [selectedDate, setSelectedDate] = useState<Date>();
    const handlePhoneInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;
        const formattedValue = value
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
            .replace(/(\-{1,2})$/g, "");

        setPhoneNumber(formattedValue);
    }
    const handleDateChange = (newDate: Date) => {
        setSelectedDate(newDate);
    }

    return (
        <Stack>
            <Title order={3}>프로필</Title>
            <PaperItem>
                <Stack align={"center"}>
                    <Avatar size={"6rem"} radius={"6rem"}/>
                    <Button className={classes["btn-primary"]}>이미지 변경</Button>
                </Stack>
                <TextInput label={"이메일"} disabled className={classes["input"]}/>
                <TextInput label={"이름"} className={classes["input"]}/>
                <TextInput label={"휴대폰 번호"}
                           maxLength={13}
                           value={phoneNumber}
                           onInput={handlePhoneInputChange}
                           className={classes["input"]}/>
                <BirthdayPicker label={"생년월일"} value={selectedDate}
                                onChange={handleDateChange}/>
                <Button className={classes["btn-primary"]}>저장하기</Button>
            </PaperItem>

            <Title order={3}>보안</Title>
            <PaperItem>
                <Group position={"apart"}>
                    <Text>비밀번호</Text>
                    <Button className={classes["btn-primary"]}>비밀번호 변경</Button>
                </Group>
            </PaperItem>

            <Title order={3}>주소</Title>
            <PaperItem>
                <TextInput label={"주소"} className={classes["input"]}/>
            </PaperItem>
        </Stack>
    );
}

export default MobileProfile;