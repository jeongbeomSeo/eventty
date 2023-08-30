import React, {useState} from "react";
import {DatePickerInput} from "@mantine/dates";
import customStyle from "../../styles/customStyle";

interface IDatePicker {
    label?: string;
    minYear?: number;
    maxYear?: number;
    placeholder?: string;
    value: Date | undefined;
    onChange: (newValue: Date) => void;
    error?: string;
}

function CalendarPicker(props:IDatePicker) {
    const {classes} = customStyle();
    const currentDate = new Date();

    return (
        <DatePickerInput label={props.label}
                         placeholder={props.placeholder}
                         valueFormat={"YYYY-MM-DD"}
                         value={props.value}
                         onChange={props.onChange}
                         firstDayOfWeek={0}
                         // minDate={new Date(currentDate.getFullYear()-120, 1, 1)}
                         // maxDate={new Date(currentDate.getFullYear()-14, currentDate.getMonth(), currentDate.getDate())}
                         getDayProps={(date) => {
                             if (date.getDay() === 6) {
                                 return {
                                     sx: () => ({
                                         color: "#3381ff",
                                     }),
                                 };
                             }
                             return {};
                         }}
                         weekendDays={[0]}
                         locale={"ko"}
                         error={props.error}
                         className={classes["input-date"]}/>
    );
}

export default CalendarPicker;