import {createStyles} from '@mantine/core';

const customStyle = createStyles((theme) => ({
    "header": {
        zIndex: 99,
        boxShadow: "0 2px 6px #e6e6e6",
        "&.mobile-event-detail":{
            position: "sticky",
        }
    },
    "input": {
        input: {
            borderColor: "#cdcdcd",
            "::placeholder": {
                color: "#cdcdcd",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        }
    },
    "input-error": {
        input: {
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
        }
    },
    "btn-primary": {
        backgroundColor: "var(--primary)",
        ":hover": {
            backgroundColor: "var(--primary)",
            filter: "brightness(0.98)",
        },
        "&.disable":{
            background: "#e6e6e6",
            color: "#b3b3b3",
            fontWeight: 800,
            cursor: "default",
        },
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
        backgroundColor: "#b3b3b3 !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "btn-gray-outline": {
        backgroundColor: "white !important",
        borderColor: "#b3b3b3 !important",
        color: "#666666 !important",
        ":hover": {
            filter: "brightness(0.98)",
        }
    },
    "signup-footer": {
        fontSize: "0.8rem",
        color: "#666666",
        paddingTop: "1rem",
    },
    "signup-divider": {
        textAlign: "center",
        whiteSpace: "pre-line",
        padding: "3rem 0 1rem 0",
        color: "#666666",
    },
    "web-nav-link": {
        fontWeight: "bold",
        ":hover": {
            opacity: "0.7",
        }
    },
    "mobile-nav-link":{
        color: "#666666",
        textAlign: "center",
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
    "ticket-select":{
        ":hover":{
            cursor:"pointer",
            borderColor: "var(--primary)",
            transition: "0.25s ease"
        }
    }
}))

export default customStyle;