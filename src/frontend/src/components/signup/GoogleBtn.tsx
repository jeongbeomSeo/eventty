import { Button } from "@mantine/core";
import customStyle from "../../styles/customStyle";

type TGoogleBtn = {
    children: string;
}

function GoogleBtn({ children }: TGoogleBtn) {
    const { classes } = customStyle();
    return (
        <Button className={classes["btn-gray-outline"]}>
            <img src={`${process.env.PUBLIC_URL}/images/google_normal.svg`} style={{paddingRight: "0.5rem"}}/>
            {children}
        </Button>
    );
}

export default GoogleBtn;