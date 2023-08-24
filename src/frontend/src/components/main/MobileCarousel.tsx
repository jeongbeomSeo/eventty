import React, {useRef} from "react";
import {Container, Paper, Title} from "@mantine/core";
import Autoplay from "embla-carousel-autoplay";
import {Carousel} from "@mantine/carousel";

const CAROUSEL_HEIGHT = "45vh";
const CAROUSEL_DELAY = 4000;
const CAROUSEL_ITEMS = [
    {color: "var(--primary)", value: "Primary"},
    {color: "#bb2649", value: "Viva Magenta"},
    {color: "#6667AB", value: "Very Peri"},
    {color: "#f5df4d", value: "Illumination"},
    {color: "#939597", value: "Ultimate Gray"}
];

function WebCarousel() {
    const autoPlay = useRef(Autoplay({delay: CAROUSEL_DELAY}));

    const items = CAROUSEL_ITEMS.map((item) => (
        <Carousel.Slide key={item.value}>
            <Paper
                p={"xl"}
                radius={"md"}
                style={{backgroundColor: item.color, height: "100%"}}>
                <Title>{item.value}</Title>
            </Paper>
        </Carousel.Slide>
    ));

    return (
        <Carousel slideSize={"80%"}
                  height={CAROUSEL_HEIGHT}
                  slidesToScroll={1}
                  slideGap={"1rem"}
                  controlSize={30}
                  // controlsOffset={"xl"}
                  align={"center"}
                  draggable
                  withControls
                  loop
                  plugins={[autoPlay.current]}
                  style={{marginTop: "3vh"}}
        >
            {items}
        </Carousel>
    );
}

export default WebCarousel;