import {useMediaQuery} from "react-responsive";
import {useMantineTheme} from "@mantine/core";

export const CheckMobile = () => {
    return (useMediaQuery({query: `(max-width:${useMantineTheme().breakpoints.xs})`}));
}

export const CheckTablet = () => {
  
}