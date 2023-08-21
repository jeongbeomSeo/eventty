import {createStyles} from '@mantine/core';

const customStyle = createStyles((theme) => ({
    "input": {
        input: {
            borderColor: "rgba(0, 0, 0, 0.2)",
            "::placeholder": {
                color: "rgba(0, 0, 0, 0.2)",
                fontWeight: "bold",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        }
    },
    "input-error": {
        input: {
            color: "rgba(0, 0, 0, 1) !important",
            "::placeholder": {
                color: "rgba(0, 0, 0, 0.2) !important",
            },
        }
    },
    "btn-primary": {
        backgroundColor: "var(--primary) !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "btn-primary-outline": {
        backgroundColor: "white !important",
        borderColor: "var(--primary) !important",
        color: "var(--primary) !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "btn-gray": {
        backgroundColor: "rgba(0, 0, 0, 0.3) !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "btn-gray-outline": {
        backgroundColor: "white !important",
        borderColor: "rgba(0, 0, 0, 0.3) !important",
        color: "rgba(0, 0, 0, 0.6) !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "signup-footer": {
        fontSize: "0.8rem",
        color: "rgba(0, 0, 0, 0.6)",
    },
    "search-box":{
        width: "80%",
        [theme.fn.smallerThan("sm")]: {
            width: "100%",
        },
        input:{
            borderColor: "var(--primary) !important",
            fontSize: "1.1rem",
            borderWidth: "2px",
        },
    },
}))

export default customStyle;