import React, {useRef} from "react";
import {Container, Title, useMantineTheme} from "@mantine/core";
import Autoplay from "embla-carousel-autoplay";
import {Carousel} from "@mantine/carousel";
import {useMediaQuery} from "react-responsive";

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
        <Carousel.Slide key={item.value} style={{backgroundColor: item.color}}>
            <Container style={{height: "100%", display: "flex", justifyContent: "center", alignItems: "center"}}>
                <Title>{item.value}</Title>
            </Container>
        </Carousel.Slide>
    ));

    return (
            <Carousel slideSize={"100%"}
                      height={CAROUSEL_HEIGHT}
                      sx={{flex: 1}}
                      draggable
                      withControls={false}
                      withIndicators
                      loop
                      plugins={[autoPlay.current]}
            >
                {items}
            </Carousel>
    );
}

export default WebCarousel;