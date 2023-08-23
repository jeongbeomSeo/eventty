import React, {useState} from "react";
import {Avatar, Burger, Container, Drawer, Flex, Group, Navbar} from "@mantine/core";

function SideNavigator() {
    const [opened, setOpened] = useState(false);
    const handleOpenSideNavigator = () => {
        setOpened((prev) => !prev);
    }

    return (
        <>
            <Drawer.Root opened={opened}
                         onClose={handleOpenSideNavigator}
                         title={"사이드 메뉴"}
                         position={"left"}
                         size={"70%"}
                         style={{zIndex:1}}
            >
                <Drawer.Overlay opacity={0.5} blur={2}/>
                <Drawer.Content>
                    <Container>
                        <Drawer.Header>
                            <Avatar radius={"xl"}/>
                            <div>타이틀</div>
                        </Drawer.Header>
                        <Drawer.Body>
                            몸통
                        </Drawer.Body>
                    </Container>
                </Drawer.Content>
            </Drawer.Root>
            <Burger opened={opened} onClick={handleOpenSideNavigator}/>
        </>
    );
}

export default SideNavigator;