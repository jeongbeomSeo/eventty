import React, {useEffect, useState} from "react";
import {
    Avatar,
    Button,
    Divider,
    FileButton,
    Flex,
    Group,
    Stack,
    TextInput,
    Title
} from "@mantine/core";
import customStyle from "../../../styles/customStyle";
import BirthdayPicker from "../../common/BirthdayPicker";
import PhoneNumberInput from "../../common/PhoneNumberInput";
import {isRouteErrorResponse, useLoaderData, useRouteError} from "react-router-dom";
import {IUpdateUser, IUser} from "../../../types/IUser";
import {useFetch} from "../../../util/hook/useFetch";
import {MessageAlert} from "../../../util/MessageAlert";
import {useModal} from "../../../util/hook/useModal";
import {Controller, useForm} from "react-hook-form";
import {Base64toFile} from "../../../util/ConvertFile";

function WebProfile() {
    const {classes} = customStyle();
    const DATA = useLoaderData() as IUser;
    const routeError = useRouteError();
    const curEmail = sessionStorage.getItem("EMAIL")!;
    const nameRegEX = /^[가-힣]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const [imgFile, setImgFile] = useState<File | null>(null);
    const [imgPreview, setImgPreview] = useState(`data:image/jpg;base64,${DATA.image}`);

    const {deleteAccountFetch, changeProfileFetch} = useFetch();
    const {changePWModal} = useModal();

    const {register, handleSubmit, setValue, control, formState: {errors}}
        = useForm<IUpdateUser>({
        defaultValues: {
            image: DATA.image ? Base64toFile(DATA.image!, DATA.originFileName!, `image/${DATA.originFileName!.split(".").pop()}`) : null,
            imageId: DATA.imageId,
            phone: DATA.phone,
            birth: new Date(DATA.birth!),
            address: DATA.address,
            name: DATA.name,
        }
    });

    const onSubmit = (data: IUpdateUser) => {
        data.birth?.setDate(data.birth?.getDate() + 1);

        const formData = new FormData();
        for (const e in data) {
            if (e === "birth") {
                formData.append(e, data[e]!.toJSON().replaceAll("\"", ""));
            } else {
                formData.append(e, data[e]);
            }
        }

        changeProfileFetch(formData);
    }

    useEffect(() => {
        if (isRouteErrorResponse(routeError)) {
            MessageAlert("error", "내 정보 조회 실패", null);
        }
    }, []);

    useEffect(() => {
        if (imgFile !== null) {
            setImgPreview(URL.createObjectURL(imgFile));
            setValue("image", imgFile);
        }
    }, [imgFile]);

    return (
        <>
            <Stack spacing={"3rem"}>
                <Stack>
                    <Title order={3}>프로필</Title>
                    <Divider/>
                    <Flex gap={"2rem"}>
                        <Stack align={"center"}>
                            <Avatar size={"8rem"} radius={"8rem"} src={imgPreview}/>
                            <FileButton onChange={setImgFile} accept={"image/png, image/jpeg, image/webp"}>
                                {(props) => <Button {...props} className={classes["btn-primary"]}>이미지 변경</Button>}
                            </FileButton>
                        </Stack>

                        <Stack style={{width: "100%"}}>

                            <TextInput label={"이메일"}
                                       disabled
                                       defaultValue={curEmail}
                                       className={classes["input"]}/>

                            <TextInput {...register("name", {
                                required: "이름을 입력해주세요",
                                pattern: {value: nameRegEX, message: "이름이 올바르지 않습니다"},
                            })}
                                       label={"이름"}
                                       withAsterisk
                                       error={errors.name?.message}
                                       className={classes["input"]}/>

                            <Controller control={control}
                                        name={"phone"}
                                        rules={{
                                            required: "휴대폰 번호를 입력해주세요",
                                            pattern: {value: phoneRegEX, message: "휴대폰 번호가 올바르지 않습니다"},
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

                            <Group position={"right"}>
                                <Button onClick={handleSubmit(onSubmit)}
                                        style={{width: "8rem"}}
                                        className={classes["btn-primary"]}>저장</Button>
                            </Group>
                        </Stack>
                    </Flex>
                </Stack>

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
                                onClick={() => deleteAccountFetch()}>
                            회원 탈퇴
                        </Button>
                    </Group>
                </Stack>
            </Stack>
        </>
    );
}

export default WebProfile;