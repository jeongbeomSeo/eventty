import React from "react";
import {Drawer} from "@mantine/core";
import {useRecoilState} from "recoil";
import {searchDrawerState} from "../../../states/searchDrawerState";

function MobileSearchDrawer() {
    const [opened, setOpened] = useRecoilState(searchDrawerState);
    const handleOpenSearch = () => {
        setOpened(prev => !prev);
    }

    return (
        <>
            <Drawer opened={opened}
                    onClose={handleOpenSearch}
                    position={"top"}
                    size={"100%"}
                    transitionProps={{duration: 400}}>
                테스트
            </Drawer>
        </>
    );
}

export default MobileSearchDrawer;