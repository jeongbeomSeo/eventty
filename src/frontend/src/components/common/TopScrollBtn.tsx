import React, {useEffect, useState} from "react";
import {ActionIcon, Button} from "@mantine/core";
import {IconArrowBigUpLinesFilled} from "@tabler/icons-react";

const SCROLL_Y = 400;

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
                        opacity: "0.5",
                    }}>
                    <IconArrowBigUpLinesFilled/>
                </ActionIcon>
            }
        </>
    );
}

export default TopScrollBtn;