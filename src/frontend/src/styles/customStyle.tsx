import {createStyles} from '@mantine/core';

const customStyle = createStyles((theme) => ({
    "header": {
        zIndex: 99,
        boxShadow: "0 2px 6px #e6e6e6",
        "&.mobile-event-detail": {
            position: "sticky",
        }
    },
    "input": {
        input: {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        "&.error": {
            input: {
                ":focus": {
                    borderColor: "red",
                },
            },
        },
    },
    "input-textarea": {
        ".mantine-Textarea-input": {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        "&.error": {
            ".mantine-Textarea-input": {
                ":focus": {
                    borderColor: "red",
                },
            },
        },
    },
    "input-date": {
        ".mantine-DatePickerInput-input": {
            ":active, :focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-DatePickerInput-day": {
            "&[data-selected], &[data-selected]:hover": {
                background: "var(--primary)",
            },
            "&[data-items='5']": {
                background: "red",
            }
        },
        ".mantine-DatePickerInput-placeholder": {
            color: "#cdcdcd !important",
        },
    },
    "input-date-time": {
        ".mantine-DateTimePicker-input": {
            ":active, :focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-DateTimePicker-day": {
            "&[data-selected], &[data-selected]:hover": {
                background: "var(--primary)",
            },
            "&[data-items='5']": {
                background: "red",
            }
        },
        ".mantine-DateTimePicker-placeholder": {
            color: "#cdcdcd !important",
        },
    },
    "input-select": {
        input: {
            borderColor: "#cdcdcd",
            color: "#000000 !important",
            "::placeholder": {
                color: "#cdcdcd !important",
            },
            ":focus": {
                borderColor: "var(--primary)",
            },
        },
        ".mantine-Select-item": {
            "&[data-selected]": {
                backgroundColor: "var(--primary) !important",
                "&[data-hovered]": {
                    filter: "brightness(0.96)",
                },
            },
        },
    },
    "input-checkbox": {
        ".mantine-Checkbox-input:checked": {
            background: "var(--primary)",
            borderColor: "var(--primary)",
        },
        "&.signup": {
            label: {
                fontSize: "0.5rem",
            },
        },
    },
    "btn-primary": {
        backgroundColor: "var(--primary)",
        ":hover": {
            backgroundColor: "var(--primary)",
            filter: "brightness(0.96)",
        },
        "&.disable": {
            background: "#e6e6e6",
            color: "#b3b3b3",
            pointerEvents: "none",
        },
    },
    "btn-primary-outline": {
        backgroundColor: "white !important",
        borderColor: "var(--primary) !important",
        color: "var(--primary) !important",
        ":hover": {
            filter: "brightness(0.95)",
        }
    },
    "btn-gray": {
        backgroundColor: "#b3b3b3 !important",
        ":hover": {
            filter: "brightness(0.96)",
        }
    },
    "btn-gray-outline": {
        backgroundColor: "white !important",
        borderColor: "#b3b3b3 !important",
        color: "#666666 !important",
        ":hover": {
            filter: "brightness(0.96)",
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
    "mobile-nav-link": {
        color: "#666666",
        textAlign: "center",
        ":active": {
            color: "var(--primary)",
        },
        "&.active": {
            color: "var(--primary)",
        },
    },
    "search-box": {
        width: "100%",
        input: {
            borderColor: "var(--primary) !important",
            fontSize: "1.1rem",
            borderWidth: "2px",
        },
    },
    "ticket-select": {
        ":hover": {
            cursor: "pointer",
            borderColor: "var(--primary)",
            transition: "0.1s ease"
        }
    },
    "category-scroll": {
        overflowX: "scroll",
        msOverflowStyle: "none",
        scrollbarWidth: "none",
        "&::-webkit-scrollbar": {
            width: 0,
        }
    },
    "tabs-primary": {
        ".mantine-Tabs-tab[data-active]": {
            borderBottom: "3px solid var(--primary) !important",
        },
    },
}))

export default customStyle;