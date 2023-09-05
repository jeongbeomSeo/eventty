import React, {useEffect, useRef, useState} from "react";
import {
    Button, Center,
    Container, Flex,
    Group, NumberInput,
    Paper,
    Select, SimpleGrid,
    Stack, Text,
    TextInput,
    Title,
    UnstyledButton
} from "@mantine/core";
import customStyle from "../styles/customStyle";
import {Controller, FormProvider, useFieldArray, useForm} from "react-hook-form";
import {IEventTicket, IEventWrite} from "../types/IEvent";
import ToastEditor from "../components/common/ToastEditor";
import {Editor} from "@toast-ui/react-editor";
import WriteHeader from "../components/display/WriteHeader";
import {
    IconBallBaseball, IconBook, IconCode,
    IconHandRock,
    IconHorseToy, IconMovie,
    IconPalette,
    IconPiano, IconPlus, IconPresentation, IconSquareRoundedPlusFilled,
    IconTent, IconX
} from "@tabler/icons-react";
import EventDatePicker from "../components/write/EventDatePicker";
import TicketSubmitModal from "../components/write/TicketSubmitModal";
import TicketEditModal from "../components/write/TicketEditModal";
import {CheckMobile} from "../util/CheckMobile";

function Write() {
    const {classes} = customStyle();
    const isMobile = CheckMobile();
    const [ticketModalOpened, setTicketModalOpened] = useState(false);
    const [ticketEditModalOpened, setTicketEditModalOpened] = useState(false);
    const editorRef = useRef<Editor>(null);

    const {register, handleSubmit, control, watch, getValues, setValue, formState: {errors}} = useForm<IEventWrite>();
    const {fields, append, remove, update} = useFieldArray({
        control,
        name: "ticket",
        rules: {
            required: "티켓을 설정해주세요",
        }
    });

    const ticketMethods = useForm<IEventTicket>();
    const ticketEditMethods = useForm<IEventTicket>();

    const currentDate = new Date();
    const CATEGORY_LIST = [
        {label: "콘서트", value: "concert", icon: <IconHandRock/>},
        {label: "클래식", value: "classic", icon: <IconPiano/>},
        {label: "전시", value: "art", icon: <IconPalette/>},
        {label: "스포츠", value: "sports", icon: <IconBallBaseball/>},
        {label: "캠핑", value: "camping", icon: <IconTent/>},
        {label: "아동", value: "kids", icon: <IconHorseToy/>},
        {label: "영화", value: "movie", icon: <IconMovie/>},
        {label: "IT", value: "it", icon: <IconCode/>},
        {label: "교양", value: "elective", icon: <IconBook/>},
        {label: "TOPIC", value: "topic", icon: <IconPresentation/>},
    ];
    const TICKET_LIMIT = 3;
    const [ticketEdit, setTicketEdit] = useState<IEventTicket | null>(null);
    const [ticketIdx, setTicketIdx] = useState(0);
    const disabledTitle = fields.map((ticket) => ticket.title);

    const onSubmit = (data: IEventWrite) => {
        data.content = editorRef.current?.getInstance().getMarkdown()!;
        console.log(data);
    }

    const onTicketCreate = (data: IEventTicket) => {
        append(data);
    }

    const onTicketEdit = (data: IEventTicket) => {
        update(ticketIdx, data);
    }

    const handleTicketModalOpened = () => {
        setTicketModalOpened(prev => !prev);
    }

    const handleTicketEditModalOpened = (data: IEventTicket, idx: number) => {
        setTicketEdit(data);
        setTicketIdx(idx);
        setTicketEditModalOpened(prev => !prev);
    }

    const ticketItems = fields.map((item, idx) => {
        return (
            <UnstyledButton key={item.id} onClick={() => handleTicketEditModalOpened(item, idx)}>
                <Paper withBorder p={"1rem"}
                       style={{width: "180px", height: "120px", position: "relative"}}
                       className={classes["ticket-select"]}>
                    <IconX size={"1rem"}
                           style={{top: "1rem", right: "1rem", position: "absolute"}}
                           onClick={(event) => {
                               event.stopPropagation();
                               remove(idx);
                           }}/>
                    <Stack spacing={"0.5rem"}>
                        <Title order={4}>{item.title}</Title>
                        <Text>{item.limit}명</Text>
                        <Text>{item.price === 0 ? "무료" : `${item.price}원`}</Text>
                    </Stack>
                </Paper>
            </UnstyledButton>
        )
    });

    console.log();

    return (
        <>
            {/* 티켓 등록 Modal */}
            <FormProvider {...ticketMethods}>
                <form onSubmit={ticketMethods.handleSubmit(onTicketCreate)}>
                    <TicketSubmitModal open={ticketModalOpened}
                                       title={disabledTitle}/>
                </form>
            </FormProvider>

            {/* 티켓 수정 Modal */}
            {ticketEdit !== null &&
                <FormProvider {...ticketEditMethods}>
                    <form onSubmit={ticketEditMethods.handleSubmit(onTicketEdit)}>
                        <TicketEditModal open={ticketEditModalOpened}
                                         title={disabledTitle}
                                         data={ticketEdit}/>
                    </form>
                </FormProvider>
            }

            <form onSubmit={handleSubmit(onSubmit)}>
                {<WriteHeader/>}
                <Container>
                    <Stack style={{margin: "5vh auto 15vh"}} spacing={"3rem"}>
                        <Stack>
                            <Title order={3}>제목</Title>
                            <TextInput {...register("title", {
                                required: "제목을 입력해주세요",
                            })}
                                       error={errors.title?.message}
                                       className={`${classes["input"]} ${errors.title && "error"}`}/>
                        </Stack>

                        <Stack align={"flex-start"}>
                            <Title order={3}>카테고리</Title>
                            <Controller control={control}
                                        name={"category"}
                                        rules={{required: "카테고리를 선택해주세요"}}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                data={CATEGORY_LIST}
                                                error={errors.category?.message}
                                                placeholder={"카테고리"}
                                                style={{width: isMobile ? "50vw" : "300px"}}
                                                className={classes["input-select"]}/>
                                        )}/>
                        </Stack>

                        <Stack align={"flex-start"}>
                            <Title order={3}>인원</Title>
                            <Controller control={control}
                                        name={"participateNum"}
                                        rules={{
                                            required: "인원을 입력해주세요",
                                            validate: (value) => value > 0 || "최소 1명 이상 입력해주세요",
                                        }}
                                        render={({field}) => (
                                            <NumberInput
                                                {...field}
                                                min={0}
                                                type={"number"}
                                                style={{width: isMobile ? "50vw" : "300px"}}
                                                error={errors.participateNum?.message}
                                                className={classes["input"]}/>
                                        )}/>
                        </Stack>

                        <Stack>
                            <Title order={3}>티켓 정보</Title>
                            <Group>
                                {ticketItems}
                                <UnstyledButton onClick={handleTicketModalOpened}
                                                hidden={ticketItems.length === TICKET_LIMIT}>
                                    <Paper style={{
                                        border: "1px dashed #cdcdcd",
                                        color: "#cdcdcd",
                                        width: "180px",
                                        height: "120px",
                                        display: "flex",
                                        justifyContent: "center",
                                        alignItems: "center"
                                    }}>
                                        <IconPlus/>
                                    </Paper>
                                </UnstyledButton>
                            </Group>
                            <Text className={"mantine-mve552"}>{errors.ticket?.root?.message}</Text>
                        </Stack>

                        <Group spacing={"2.5rem"}>
                            <Stack>
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
                                                            error={errors.eventStartAt?.message}/>
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
                                                            error={errors.eventEndAt?.message}/>
                                                    )}/>
                                    </Group>
                                </Stack>
                            </Stack>
                            <Stack>
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
                                                            error={errors.applyStartAt?.message}/>
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
                                                            error={errors.applyEndAt?.message}/>
                                                    )}/>
                                    </Group>
                                </Stack>
                            </Stack>
                        </Group>

                        <Stack>
                            <Title order={3}>커버 이미지</Title>
                        </Stack>

                        <Stack>
                            <Title order={3}>내용</Title>
                            <Controller control={control}
                                        name={"content"}
                                        rules={{required: "내용을 입력해주세요"}}
                                        defaultValue={""}
                                        render={({field}) => (
                                            <>
                                                <ToastEditor
                                                    content={field.value}
                                                    onChange={field.onChange}
                                                    editorRef={editorRef}/>
                                                <Text className={"mantine-mve552"}>{errors.content?.message}</Text>
                                            </>
                                        )}/>
                        </Stack>

                        <Stack>
                            <Title order={3}>장소</Title>
                            <TextInput {...register("location", {
                                required: "장소를 입력해주세요",
                            })}
                                       error={errors.location?.message}
                                       className={`${classes["input"]} ${errors.location && "error"}`}/>
                        </Stack>
                    </Stack>
                </Container>
            </form>
        </>
    );
}

export default Write;