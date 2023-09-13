import React from "react";
import {Grid, Text, UnstyledButton} from "@mantine/core";
import {IconClock, IconX} from "@tabler/icons-react";

interface IKeywords {
    item: string;
    onClick: (keyword: string) => void;
    onDelete: (keyword: string) => void;
}

function SearchKeywordsItem({item, onClick, onDelete}: IKeywords) {
    return (
        <Grid style={{alignItems: "center"}}>
            <Grid.Col span={1} style={{textAlign: "center"}}>
                <IconClock color={"#666666"}/>
            </Grid.Col>
            <Grid.Col span={"auto"}>
                <UnstyledButton onClick={() => onClick(item)} style={{width:"100%"}}>
                    <Text lineClamp={2} style={{wordBreak:"break-all"}}>{item}</Text>
                </UnstyledButton>
            </Grid.Col>
            <Grid.Col span={1} style={{textAlign: "center"}}>
                <UnstyledButton onClick={() => onDelete(item)}>
                    <IconX size={"1rem"}
                           color={"#666666"}/>
                </UnstyledButton>
            </Grid.Col>
        </Grid>
    );
}

export default SearchKeywordsItem;