import React, {ChangeEvent, useEffect, useState} from "react";
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
import {isRouteErrorResponse, Link, useLoaderData, useRouteError} from "react-router-dom";
import {IUser} from "../../../types/IUser";
import {useFetch} from "../../../util/hook/useFetch";
import {MessageAlert} from "../../../util/MessageAlert";
import {useModal} from "../../../util/hook/useModal";
import {Controller, useForm} from "react-hook-form";
import {patchProfile} from "../../../service/user/fetchUser";

function WebProfile() {
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
        <>
            <Stack spacing={"3rem"}>

                <form>
                    <Stack>
                        <Title order={3}>프로필</Title>
                        <Divider/>
                        <Flex gap={"2rem"}>
                            <Stack align={"center"}>
                                <Avatar size={"8rem"}
                                        radius={"8rem"}
                                        src={DATA.image}
                                />
                                <Button className={classes["btn-primary"]}>이미지 변경</Button>
                            </Stack>

                            <Stack style={{width: "100%"}}>

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
                                            render={({field: {ref,...rest}}) => (
                                                <BirthdayPicker {...rest}
                                                    inputRef={ref}
                                                    label={"생년월일"}/>
                                            )}/>

                                <TextInput {...register("address")}
                                           label={"주소"}
                                           defaultValue={DATA.address}
                                           className={classes["input"]}/>

                                <Group position={"right"}>
                                    <Button onClick={handleSubmit(onSubmit)}
                                            style={{width: "8rem"}}
                                            className={classes["btn-primary"]}>저장</Button>
                                </Group>
                            </Stack>
                        </Flex>
                    </Stack>
                </form>


                <Stack>
                    <Title order={3}>보안</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>비밀번호</Title>
                        <Button onClick={() => changePWModal()}
                                style={{width: "8rem"}}
                                className={classes["btn-primary"]}>
                            비밀번호 변경
                        </Button>
                    </Group>
                </Stack>

                <Stack>
                    <Title order={3} color={"red"}>회원 탈퇴</Title>
                    <Divider/>
                    <Group position={"apart"}>
                        <Title order={5}>회원 탈퇴</Title>
                        <Button color={"red"}
                                style={{width: "8rem"}}
                                onClick={() => deleteAccountFetch()}>회원 탈퇴</Button>
                    </Group>
                </Stack>
            </Stack>
        </>
    );
}

export default WebProfile;