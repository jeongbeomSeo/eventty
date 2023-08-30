import React, {ChangeEvent} from "react";
import {TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";

interface IPhoneNumber {
    value: string;
    onInput: (formattedValue: string) => void;
}

function PhoneNumberInput(props:IPhoneNumber) {
    const {classes} = customStyle();
    const handlePhoneInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;
        const formattedValue = value
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
            .replace(/(\-{1,2})$/g, "");

        props.onInput(formattedValue);
    }

    return (
        <TextInput label={"휴대폰 번호"}
                   maxLength={13}
                   value={props.value}
                   onInput={handlePhoneInputChange}
                   className={classes["input"]}/>
    );
}

export default PhoneNumberInput;