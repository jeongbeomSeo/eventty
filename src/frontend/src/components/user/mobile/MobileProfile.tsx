import React, {ChangeEvent, useEffect, useState} from "react";
import {Avatar, Button, Center, Flex, Group, Paper, Stack, Text, TextInput, Title} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import {DatePickerInput} from "@mantine/dates";
import BirthdayPicker from "../../common/BirthdayPicker";
import {isRouteErrorResponse, useLoaderData, useRouteError} from "react-router-dom";
import {IUser} from "../../../types/IUser";
import {useFetch} from "../../../util/hook/useFetch";
import {useModal} from "../../../util/hook/useModal";
import {Controller, useForm} from "react-hook-form";
import {MessageAlert} from "../../../util/MessageAlert";
import PhoneNumberInput from "../../common/PhoneNumberInput";

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
    const DATA = useLoaderData() as IUser;
    const routeError = useRouteError();
    const curEmail = sessionStorage.getItem("EMAIL")!;

    const {deleteAccountFetch, changeProfileFetch} = useFetch();
    const {changePWModal} = useModal();

    const {register, handleSubmit, control, formState: {errors}}
        = useForm<IUser>({
        defaultValues: {
            image: DATA.image,
            phone: DATA.phone,
            birth: DATA.birth,
            address: DATA.address,
            name: DATA.name,
            userId: DATA.userId,
        }
    });

    const onSubmit = (data: IUser) => {
        changeProfileFetch(data);
    }

    useEffect(() => {
        if (isRouteErrorResponse(routeError)) {
            MessageAlert("error", "내 정보 조회 실패", null);
        }
    }, []);

    return (
        <Stack spacing={"2rem"}>
            <div>
                <Title order={3}>프로필</Title>
                <PaperItem>
                    <Stack align={"center"}>
                        <Avatar size={"6rem"} radius={"6rem"}/>
                        <Button className={classes["btn-primary"]}>이미지 변경</Button>
                    </Stack>

                    <TextInput label={"이메일"}
                               disabled
                               defaultValue={curEmail}
                               className={classes["input"]}/>

                    <TextInput {...register("name", {
                        required: "이름을 입력해주세요",
                        minLength: {value: 2, message: "2글자 이상 입력해주세요"},
                    })}
                               label={"이름"}
                               withAsterisk
                               error={errors.name?.message}
                               className={classes["input"]}/>

                    <Controller control={control}
                                name={"phone"}
                                rules={{
                                    required: "휴대폰 번호를 입력해주세요",
                                }}
                                render={({field: {ref, ...rest}}) => (
                                    <PhoneNumberInput {...rest}
                                                      inputRef={ref}
                                                      error={errors.phone?.message}
                                                      asterisk={true}/>
                                )}/>

                    <Controller control={control}
                                name={"birth"}
                                render={({field: {ref, ...rest}}) => (
                                    <BirthdayPicker {...rest}
                                                    inputRef={ref}
                                                    label={"생년월일"}/>
                                )}/>

                    <TextInput {...register("address")}
                               label={"주소"}
                               defaultValue={DATA.address}
                               className={classes["input"]}/>

                    <Button onClick={handleSubmit(onSubmit)}
                            className={classes["btn-primary"]}>
                        저장하기
                    </Button>
                </PaperItem>
            </div>

            <div>
                <Title order={3}>보안</Title>
                <PaperItem>
                    <Group position={"apart"}>
                        <Text>비밀번호</Text>
                        <Button onClick={() => changePWModal()}
                                style={{width: "8rem"}}
                                className={classes["btn-primary"]}>
                            비밀번호 변경
                        </Button>
                    </Group>
                </PaperItem>
            </div>

            <div>
                <Title order={3}>회원탈퇴</Title>
                <PaperItem>
                    <Group position={"apart"}>
                        <Text>회원탈퇴</Text>
                        <Button color={"red"}
                                style={{width: "8rem"}}
                                onClick={() => deleteAccountFetch()}>
                            회원탈퇴
                        </Button>
                    </Group>
                </PaperItem>
            </div>
        </Stack>
    );
}

export default MobileProfile;