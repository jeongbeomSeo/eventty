import React from "react";
import {Button, TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {IconSearch} from "@tabler/icons-react";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {modals} from "@mantine/modals";

interface ISearch {
    keyword: string;
}

function SearchBox({onAddKeyword}: { onAddKeyword: (keyword: string) => void }) {
    const {classes} = customStyle();
    const navigate = useNavigate();
    const {register, handleSubmit, reset} = useForm<ISearch>();

    const onSubmit = (data: ISearch) => {
        if (data.keyword !== "") {
            onAddKeyword(data.keyword);
            modals.closeAll();
            reset();
            navigate(`/events?search=${data.keyword}`);
        }
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)} style={{width: "100%"}}>
            <TextInput {...register("keyword")}
                       size={"md"}
                       radius={"md"}
                       icon={<IconSearch/>}
                       className={`${classes["input"]} search`}
            />
            <Button type={"submit"} display={"none"}/>
        </form>
    );
}

export default SearchBox;