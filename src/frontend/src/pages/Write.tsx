import React, {useRef, useState} from "react";
import {Button, Container, Group, Input, Select, Stack, Textarea, TextInput, Title} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {DatePicker, DateTimePicker, TimeInput} from "@mantine/dates";
import {Controller, useForm} from "react-hook-form";
import {IEvent, IEventWrite} from "../types/IEvent";
import ToastEditor from "../components/common/ToastEditor";
import {Editor} from "@toast-ui/react-editor";
import WriteHeader from "../components/display/WriteHeader";
import {
    IconBallBaseball, IconBook,
    IconCalendar, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette,
    IconPiano, IconPresentation,
    IconTent
} from "@tabler/icons-react";
import EventDatePicker from "../components/common/EventDatePicker";

function Write() {
    const {classes} = customStyle();
    const editorRef = useRef<Editor>(null);

    const {register, handleSubmit, control, watch, getValues, formState: {errors}} = useForm<IEventWrite>();

    const currentDate = new Date();
    const CATEGORY_LIST = [
        {label: "콘서트", value: "1", icon: <IconHandRock/>},
        {label: "클래식", value: "2", icon: <IconPiano/>},
        {label: "전시", value: "3", icon: <IconPalette/>},
        {label: "스포츠", value: "4", icon: <IconBallBaseball/>},
        {label: `캠핑`, value: "5", icon: <IconTent/>},
        {label: "아동", value: "6", icon: <IconHorseToy/>},
        {label: "영화", value: "7", icon: <IconMovie/>},
        {label: "IT", value: "8", icon: <IconCode/>},
        {label: "교양", value: "9", icon: <IconBook/>},
        {label: "TOPIC", value: "10", icon: <IconPresentation/>},
    ];

    const onSubmit = (data: IEventWrite) => {

    }

    console.log(watch("eventStartAt") + "\n" + watch("eventEndAt"))

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)}>
                {<WriteHeader/>}
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
                                {/*<Button compact radius={"5rem"} className={classes["btn-primary"]}>category</Button>
                                <Button compact radius={"5rem"}
                                        className={classes["btn-primary-outline"]}>category</Button>*/}
                                <Controller control={control}
                                            name={"category"}
                                            rules={{required: "카테고리를 선택해주세요"}}
                                            render={({field}) => (
                                                <Select
                                                    {...field}
                                                    data={CATEGORY_LIST}
                                                    error={errors.category && errors.category.message}/>
                                            )}/>
                            </Group>
                        </div>

                        <div>
                            <Title order={3}>티켓 정보</Title>
                            <div>티켓 영역</div>
                        </div>

                        <Group spacing={"2.5rem"}>
                            <div>
                                <Title order={3}>행사 일정</Title>
                                <Stack>
                                    <Group>
                                        <Title order={4}>시작</Title>
                                        <Controller control={control}
                                                    name={"eventStartAt"}
                                                    rules={{required: "날짜를 지정해주세요",}}
                                                    render={({field}) => (
                                                        <EventDatePicker
                                                            {...field}
                                                            minDate={currentDate}
                                                            error={errors.eventStartAt && errors.eventStartAt.message}/>
                                                    )}/>
                                    </Group>

                                    <Group>
                                        <Title order={4}>종료</Title>
                                        <Controller control={control}
                                                    name={"eventEndAt"}
                                                    rules={{
                                                        required: "날짜를 지정해주세요",
                                                        validate: (value) => new Date(getValues("eventStartAt")) <= value || "종료일 확인",
                                                    }}
                                                    render={({field}) => (
                                                        <EventDatePicker
                                                            {...field}
                                                            minDate={watch("eventStartAt")}
                                                            error={errors.eventEndAt && errors.eventEndAt.message}/>
                                                    )}/>
                                    </Group>
                                </Stack>
                            </div>
                            <div>
                                <Title order={3}>예약 일정</Title>
                                <Stack>
                                    <Group>
                                        <Title order={4}>시작</Title>
                                        <Controller control={control}
                                                    name={"applyStartAt"}
                                                    rules={{required: "날짜를 지정해주세요",}}
                                                    render={({field}) => (
                                                        <EventDatePicker
                                                            {...field}
                                                            minDate={currentDate}
                                                            error={errors.applyStartAt && errors.applyStartAt.message}/>
                                                    )}/>
                                    </Group>

                                    <Group>
                                        <Title order={4}>종료</Title>
                                        <Controller control={control}
                                                    name={"applyEndAt"}
                                                    rules={{
                                                        required: "날짜를 지정해주세요",
                                                        validate: (value) => new Date(getValues("applyEndAt")) <= value || "종료일 확인",
                                                    }}
                                                    render={({field}) => (
                                                        <EventDatePicker
                                                            {...field}
                                                            minDate={watch("applyStartAt")}
                                                            error={errors.applyEndAt && errors.applyEndAt.message}/>
                                                    )}/>
                                    </Group>
                                </Stack>
                            </div>
                        </Group>

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