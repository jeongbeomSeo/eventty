import React, {useRef} from "react";
import {Button, Container, Group, Stack, Textarea, TextInput, Title} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {DatePickerInput, DateTimePicker, TimeInput} from "@mantine/dates";
import {useForm} from "react-hook-form";
import {IEvent} from "../types/IEvent";
import ToastEditor from "../components/common/ToastEditor";
import {Editor} from "@toast-ui/react-editor";
import WriteHeader from "../components/display/WriteHeader";
import {IconCalendar} from "@tabler/icons-react";

function Write() {
    const {classes} = customStyle();
    const ref = useRef<HTMLInputElement>(null);
    const editorRef = useRef<Editor>(null);

    const {register, handleSubmit, formState: {errors}} = useForm<IEvent>();

    const onSubmit = () => {
        console.log(editorRef.current?.getInstance().getMarkdown());
    }

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)}>
                {<WriteHeader onSubmit={onSubmit}/>}
                <Container>
                    <Stack style={{margin: "5vh auto 15vh"}} spacing={"3rem"}>
                        <div>
                            <Title order={3}>제목</Title>
                            <TextInput {...register("title", {
                                required: "제목을 입력해주세요",
                            })}
                                       error={errors.title && errors.title?.message}
                                       className={`${classes["input"]} ${errors.title && "error"}`}/>
                        </div>

                        <div>
                            <Title order={3}>카테고리</Title>
                            <Group spacing={"0.5rem"}>
                                <Button compact radius={"5rem"} className={classes["btn-primary"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>
                            </Group>
                        </div>

                        <div>
                            <Title order={3}>티켓 정보</Title>
                            <div>티켓 영역</div>
                        </div>

                        <div>
                            <Title order={3}>행사 일정</Title>
                            <Stack>
                                <Group>
                                    <Title order={4}>시작</Title>
                                    <DateTimePicker
                                        icon={<IconCalendar/>}
                                        locale={"ko"}
                                        valueFormat={"YYYY-MM-DD HH시 mm분"}
                                        timeInputProps={{"ref": ref, onClick: () => ref.current?.showPicker()}}
                                        style={{width: "15rem"}}
                                        className={classes["input"]}/>
                                </Group>

                                <Group>
                                    <Title order={4}>종료</Title>
                                    <DateTimePicker
                                        icon={<IconCalendar/>}
                                        locale={"ko"}
                                        valueFormat={"YYYY-MM-DD HH시 mm분"}
                                        timeInputProps={{"ref": ref, onClick: () => ref.current?.showPicker()}}
                                        style={{width: "15rem"}}
                                        className={classes["input"]}/>
                                </Group>
                            </Stack>
                        </div>

                        <Title order={3}>예약 종료</Title>
                        <Title order={3}>커버 이미지</Title>

                        <div>
                            <Title order={3}>소개</Title>
                            <Textarea
                                placeholder={"150자 이내로 작성해주세요"}
                                maxLength={150}
                                minRows={4}
                                className={classes["input-textarea"]}/>
                        </div>

                        <div>
                            <Title order={3}>내용</Title>
                            <ToastEditor editorRef={editorRef}/>
                        </div>

                        <div>
                            <Title order={3}>장소</Title>
                            <TextInput {...register("location", {
                                required: "장소를 입력해주세요",
                            })}
                                       error={errors.location && errors.location?.message}
                                       className={`${classes["input"]} ${errors.location && "error"}`}/>
                        </div>
                    </Stack>
                </Container>
            </form>
        </>
    );
}

export default Write;