import React, {useEffect, useState} from "react";
import {ActionIcon, Button, TextInput} from "@mantine/core";
import customStyle from "../../styles/customStyle";
import {IconSearch} from "@tabler/icons-react";
import {Link} from "react-router-dom";
import {useForm} from "react-hook-form";
import {ISignup} from "../../types/IUser";
import {loadingState} from "../../states/loadingState";
import {SearchRecentHistory} from "../../util/SearchRecentHistory";

interface ISearch {
    keyword: string;
}

function SearchBox() {
    const {classes} = customStyle();
    const { register, handleSubmit, reset } = useForm<ISearch>();
    const {handleAddKeyword} = SearchRecentHistory();

    const onSubmit = (data:ISearch) => {
        if (data.keyword !== ""){
            handleAddKeyword(data.keyword);
            reset();
        }
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)} style={{width:"100%"}}>
            <TextInput {...register("keyword")}
                size={"md"}
                radius={"md"}
                icon={<IconSearch/>}
                className={`${classes["input"]} search`}
            />
            <Button type={"submit"} display={"none"} />
        </form>
    );
}

export default SearchBox;