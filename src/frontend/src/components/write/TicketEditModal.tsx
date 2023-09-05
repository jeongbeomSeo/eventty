import React, {useEffect, useState} from "react";
import {Button, Checkbox, Group, Modal, NumberInput, Select, Stack, TextInput} from "@mantine/core";
import {Controller, useFormContext} from "react-hook-form";
import customStyle from "../../styles/customStyle";
import {IEventTicket} from "../../types/IEvent";
import {getValue} from "@testing-library/user-event/dist/utils";

function TicketEditModal({open, title, data}:{open:boolean, title:string[], data:IEventTicket}) {
    const {classes} = customStyle();

    const [modalOpened, setModalOpened] = useState(open)
    const [ticketPriceFree, setTicketPriceFree] = useState(false);

    const {register, handleSubmit, control, getValues, setValue, watch, reset, resetField, clearErrors, formState: {errors}} = useFormContext<IEventTicket>();

    const disabledTitle = title.filter(item => item !== data.title);

    const handleTicketPriceFree = () => {
        clearErrors("price");
        setTicketPriceFree(prev => !prev);
        ticketPriceFree ? resetField("price") : setValue("price", 0);
    }

    const handleModalOpened = () => {
        setTicketPriceFree(false);
        setModalOpened(prev => !prev);
    }
    const onSubmit = () => {
        handleModalOpened();
    }

    useEffect(() => {
        handleModalOpened();
    }, [open]);

    useEffect(() => {
        setValue("id", data.id);
        setValue("title", data.title);
        setValue("price", data.price);
        setValue("limit", data.limit);

        if (data.price === 0){
            setTicketPriceFree(true);
        }
    }, [data]);
    
    return (
        <Modal opened={modalOpened}
               onClose={handleModalOpened}
               withCloseButton={false}
               xOffset={""}
               size={"auto"}
               centered>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Stack style={{padding:"1rem"}}>
                    <Controller control={control}
                                name={"title"}
                                rules={{required: "종류를 선택해주세요"}}
                                render={({field}) => (
                                    <Select
                                        {...field}
                                        label="티켓 종류"
                                        data={[
                                            {value: "얼리버드", label: "얼리버드", disabled: disabledTitle.includes("얼리버드")},
                                            {value: "일반", label: "일반", disabled: disabledTitle.includes("일반")},
                                            {value: "VIP", label: "VIP", disabled: disabledTitle.includes("VIP")},]}
                                        defaultValue={data.title}
                                        error={errors.title && errors.title.message}
                                        className={classes["input-select"]}/>
                                )}/>

                    <Controller control={control}
                                name={"price"}
                                rules={{
                                    required: "금액을 입력해주세요",
                                    validate: (value) => ((value >= 100 && !ticketPriceFree) || ticketPriceFree) || "최소 100원 이상 입력해주세요"
                                }}
                                render={({field}) => (
                                    <NumberInput
                                        {...field}
                                        label={"금액"}
                                        defaultValue={data.price}
                                        disabled={ticketPriceFree}
                                        type={"number"}
                                        hideControls
                                        error={errors.price && errors.price.message}
                                        className={classes["input"]}/>
                                )}/>
                    <Checkbox label={"무료"}
                              checked={ticketPriceFree}
                              onChange={handleTicketPriceFree}
                              className={classes["input-checkbox"]}/>
                    <Controller control={control}
                                name={"limit"}
                                rules={{required: "인원을 정해주세요",
                                validate: (value) => value > 0 || "최소 1명 이상 입력해주세요"}}
                                render={({field}) => (
                                    <NumberInput
                                        {...field}
                                        label={"인원"}
                                        min={0}
                                        type={"number"}
                                        defaultValue={data.limit}
                                        error={errors.limit && errors.limit.message}
                                        className={classes["input"]}/>
                                )}/>
                    <Group grow>
                        <Button onClick={handleModalOpened} className={classes["btn-primary-outline"]}>취소</Button>
                        <Button type={"submit"} className={classes["btn-primary"]}>등록</Button>
                    </Group>
                </Stack>
            </form>
        </Modal>
    );
}

export default TicketEditModal;