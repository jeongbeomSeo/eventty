import React, {useRef} from "react";
import {BackgroundImage, Container, Image, Title} from "@mantine/core";
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

    const items = CAROUSEL_ITEMS.map((item, idx) => (
        <Carousel.Slide key={idx} style={{height: "100%", background: "red"}}>
            <Image height={"100%"}
                src={`${process.env["REACT_APP_NCLOUD_ENDPOINT"]}/${process.env["REACT_APP_NCLOUD_BUCKET_NAME"]}/main/web/web_carousel_0${idx + 1}.webp`}/>
        </Carousel.Slide>
    ));


    return (
        <Carousel slideSize={"100%"}
                  height={CAROUSEL_HEIGHT}
                  sx={{flex: 1}}
                  draggable
                  withControls
                  withIndicators
                  loop
                  plugins={[autoPlay.current]}
                  styles={{
                      indicator: {
                          width: "0.5rem",
                          height: "0.5rem",
                          transition: "width 250ms ease",
                          "&[data-active]": {
                              width: "2.5rem",
                          },
                      },
                  }}
        >
            {items}
        </Carousel>
    );
}

export default WebCarousel;