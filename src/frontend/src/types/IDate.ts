import {Ref} from "react";

export interface IDatePicker {
    label?: string;
    minDate?: Date;
    maxYear?: number;
    placeholder?: string;
    value: Date | undefined;
    onChange: (newValue: Date) => void;
    error?: string;
}

export interface IDateTimePicker {
    label?: string;
    minDate?: Date;
    maxYear?: number;
    placeholder?: string;
    value: Date | undefined;
    onChange: (newValue: Date) => void;
    error?: string;
}