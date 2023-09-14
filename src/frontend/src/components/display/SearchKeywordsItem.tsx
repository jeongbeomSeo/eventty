import React from "react";
import {Grid, Text, UnstyledButton} from "@mantine/core";
import {IconClock, IconX} from "@tabler/icons-react";
import {useNavigate} from "react-router-dom";
import {modals} from "@mantine/modals";

interface IKeywords {
    item: string;
    onClick: (keyword: string) => void;
    onDelete: (keyword: string) => void;
}

function SearchKeywordsItem({item, onClick, onDelete}: IKeywords) {
    const navigate = useNavigate();
    return (
        <UnstyledButton onClick={() => {
            onClick(item);
            navigate(`/events?search=${item}`);
            modals.closeAll();
        }} style={{width: "100%"}}>
            <Grid style={{alignItems: "center"}}>
                <Grid.Col span={1} style={{textAlign: "center"}}>
                    <IconClock color={"#666666"}/>
                </Grid.Col>
                <Grid.Col span={"auto"}>
                    <Text lineClamp={2} style={{wordBreak: "break-all"}}>{item}</Text>
                </Grid.Col>
                <Grid.Col span={1} style={{textAlign: "center"}}>
                    <UnstyledButton onClick={(event) => {
                        event.stopPropagation();
                        onDelete(item);
                    }}>
                        <IconX size={"1rem"}
                               color={"#666666"}/>
                    </UnstyledButton>
                </Grid.Col>
            </Grid>
        </UnstyledButton>
    );
}

export default SearchKeywordsItem;