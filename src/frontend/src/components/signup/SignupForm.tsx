import {Button, Checkbox, Grid, Stack, TextInput} from "@mantine/core";
import {useNavigate} from "react-router-dom";
import {useRecoilState} from "recoil";
import {userState} from "../../states/userState";
import {Controller, useForm} from "react-hook-form";
import customStyles from "../../styles/customStyle";
import {ChangeEvent, useState} from "react";
import {ISignup} from "../../types/IUser";
import {DatePickerInput} from "@mantine/dates";
import "dayjs/locale/ko";
import CalendarPicker from "../common/CalendarPicker";
import PhoneNumberInput from "../common/PhoneNumberInput";

function SignupForm(props: { isHost: boolean }) {
    const navigate = useNavigate();
    const [userStateValue, setUserStateValue] = useRecoilState(userState);

    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    const nameRegEX = /^[가-힣]{2,}$/;
    const phoneRegEX = /^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})$/;
    const {
        register,
        handleSubmit,
        getValues,
        setValue,
        control,
        watch,
        formState: {errors}
    } = useForm<ISignup>({mode: "onChange"});

    const emailInputValue = watch("email");
    const nicknameInputValue = watch("nickname");
    const [isEmailValid, setIsEmailValid] = useState(false);
    const [isNicknameValid, setIsNicknameValid] = useState(false);

    const onSubmit = (data: ISignup) => {
        if (isEmailValid && isNicknameValid) {
            setUserStateValue({
                ...data,
                ...props
            });
            navigate("/");
        } else {
            !isEmailValid ?
                alert("이메일 중복 확인해주세요") :
                alert("닉네임 중복 확인해주세요");
        }
    };

    const onEmailValid = () => {
        if (typeof emailInputValue !== "undefined" && emailInputValue !== "") {
            if (errors.email) {
                alert("사용 불가능");
                setIsEmailValid(false);
            } else {
                if (userStateValue.email !== emailInputValue) {
                    alert("사용 가능");
                    setIsEmailValid(true);
                } else {
                    alert("중복된 이메일");
                    setIsEmailValid(false);
                }
            }
        } else {
            alert("이메일을 입력해주세요")
            setIsEmailValid(false);
        }
    }

    const onNicknameValid = () => {
        if (typeof nicknameInputValue !== "undefined" && nicknameInputValue !== "") {
            if (errors.nickname) {
                alert("사용 불가능");
                setIsNicknameValid(false);
            } else {
                if (userStateValue.nickname !== nicknameInputValue) {
                    alert("사용 가능");
                    setIsNicknameValid(true);
                } else {
                    alert("중복된 닉네임");
                    setIsNicknameValid(false);
                }
            }
        } else {
            alert("닉네임을 입력해주세요")
            setIsNicknameValid(false);
        }
    }

    const handlePhoneInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {value} = e.target;
        const formattedValue = value
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
            .replace(/(\-{1,2})$/g, "");

        setValue("phone", formattedValue);
    }

    const {classes} = customStyles();

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Stack>
                <Grid>
                    <Grid.Col span={"auto"}>
                        <TextInput {...register("email", {
                            required: "이메일을 입력해주세요",
                            pattern: {
                                value: emailRegEx,
                                message: "이메일이 올바르지 않습니다",
                            }
                        })}
                                   placeholder="이메일"
                                   label="이메일"
                                   error={errors.email && errors.email?.message}
                                   className={`${classes["input"]} ${errors.email && "error"}`}
                        />
                    </Grid.Col>
                    <Grid.Col span={"content"}>
                        <Button className={classes["btn-primary"]} style={{marginTop: "24px"}}
                                onClick={onEmailValid}>중복확인</Button>
                    </Grid.Col>
                </Grid>
                <TextInput
                    {...register("password", {
                        required: "비밀번호를 입력해주세요",
                        minLength: {
                            value: 8,
                            message: "최소 8자 이상 비밀번호를 입력해주세요"
                        },
                        maxLength: {
                            value: 16,
                            message: "16자 이하 비밀번호만 사용 가능합니다"
                        }
                    })}
                    type="password"
                    placeholder="비밀번호"
                    label="비밀번호"
                    error={errors.password && errors.password?.message}
                    className={`${classes["input"]} ${errors.password && "error"}`}/>
                <TextInput {...register("passwordConfirm", {
                    required: "비밀번호를 다시 입력해주세요",
                    validate: {
                        check: (value) => {
                            if (getValues("password") !== value) {
                                return "비밀번호가 일치하지 않습니다"
                            }
                        }
                    }
                })}
                           type="password"
                           placeholder="비밀번호 확인"
                           error={errors.passwordConfirm && errors.passwordConfirm?.message}
                           className={`${classes["input"]} ${errors.passwordConfirm && "error"}`}/>
                <TextInput {...register("name", {
                    required: "이름을 입력해주세요",
                    pattern: {
                        value: nameRegEX,
                        message: "이름이 올바르지 않습니다",
                    }
                })}
                           placeholder="이름"
                           label="이름"
                           error={errors.name && errors.name?.message}
                           className={`${classes["input"]} ${errors.name && "error"}`}/>
                <TextInput {...register("phone", {
                    required: "휴대폰 번호를 입력해주세요",
                    pattern: {
                        value: phoneRegEX,
                        message: "휴대폰 번호가 올바르지 않습니다",
                    },
                })}
                           placeholder="휴대폰 번호"
                           label="휴대폰 번호"
                           maxLength={13}
                           error={errors.phone && errors.phone?.message}
                           className={`${classes["input"]} ${errors.phone && "error"}`}
                           onInput={handlePhoneInputChange}/>
                <Grid>
                    <Grid.Col span={"auto"}>
                        <TextInput {...register("nickname", {
                            required: "닉네임을 입력해주세요",
                            minLength: {
                                value: 2,
                                message: "최소 2글자 이상 입력해주세요",
                            },
                        })}
                                   placeholder={"닉네임"}
                                   label={"닉네임"}
                                   error={errors.nickname && errors.nickname?.message}
                                   className={`${classes["input"]} ${errors.nickname && "error"}`}/>
                    </Grid.Col>
                    <Grid.Col span={"content"}>
                        <Button className={classes["btn-primary"]} style={{marginTop: "24px"}}
                                onClick={onNicknameValid}>중복확인</Button>
                    </Grid.Col>
                </Grid>
                <Controller control={control}
                            name={"birth"}
                            rules={{required: "날짜를 선택해주세요"}}
                            render={({field}) => (
                                <CalendarPicker label={"생년월일"}
                                                placeholder={"생년월일"}
                                                value={field.value}
                                                onChange={field.onChange}
                                                error={errors.birth && errors.birth?.message}/>
                            )}/>
                <TextInput {...register("address", {
                    required: "주소를 입력해주세요"
                })}
                           placeholder="주소"
                           label="주소"
                           error={errors.address && errors.address?.message}
                           className={`${classes["input"]} ${errors.address && "error"}`}/>
                <Checkbox {...register("termsOfService",{
                    required: "약관에 동의해주세요"
                })}
                          label={"서비스 이용 약관과 개인정보 취급 방침 및 개인정보 3자 제공 동의"}
                          error={errors.termsOfService && errors.termsOfService?.message}
                          className={classes["signup-checkbox"]}
                />
                <Button type="submit" className={classes["btn-primary"]}>
                    회원가입
                </Button>
            </Stack>
        </form>
    );
}

export default SignupForm;