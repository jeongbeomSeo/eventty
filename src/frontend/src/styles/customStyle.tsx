import {createStyles} from '@mantine/core';

const customStyle = createStyles((theme) => ({
    "header": {
        zIndex: 99,
        boxShadow: "0 2px 6px rgba(0, 0, 0, 0.1)",
        position: "sticky",
    },
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
        paddingTop: "1rem",
    },
    "signup-divider": {
        textAlign: "center",
        whiteSpace: "pre-line",
        padding: "3rem 0 1rem 0",
        color: "rgba(0, 0, 0, 0.6)",
    },
    "web-nav-link": {
        fontWeight: "bold",
        ":hover": {
            opacity: "0.7",
        }
    },
    "mobile-nav-link":{
        color: "rgba(0, 0, 0, 0.6)",
        ":active":{
            color: "var(--primary)",
        },
        "&.active":{
            color: "var(--primary)",
        },
    },
    "search-box": {
        width: "80%",
        [theme.fn.smallerThan("sm")]: {
            width: "100%",
        },
        input: {
            borderColor: "var(--primary) !important",
            fontSize: "1.1rem",
            borderWidth: "2px",
        },
    },
}))

export default customStyle;