import React, {useEffect, useState} from "react";
import {ActionIcon, Button, LoadingOverlay} from "@mantine/core";
import {IconArrowBigUpLinesFilled} from "@tabler/icons-react";
import {useNavigation} from "react-router-dom";

const SCROLL_Y = 370;

function TopScrollBtn() {
    const [showButton, setShowButton] = useState(false);
    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth",
        })
    }

    useEffect(() => {
        const handleShowButton = () => {
            setShowButton((window.scrollY > SCROLL_Y));
        }

        window.addEventListener("scroll", handleShowButton);
        return ()=>{
            window.removeEventListener( "scroll", handleShowButton);
        }
    }, []);

    return (
        <>
            {showButton &&
                <ActionIcon
                    variant={"outline"}
                    radius={"xl"}
                    onClick={scrollToTop}
                    style={{
                        position: "fixed",
                        bottom: "1rem",
                        right: "1rem",
                        width: "50px",
                        height: "50px",
                        opacity: "0.4",
                        zIndex: "98",
                        background: "white",
                    }}>
                    <IconArrowBigUpLinesFilled/>
                </ActionIcon>
            }
        </>
    );
}

export default TopScrollBtn;