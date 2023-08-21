import {Button, Grid, Stack, TextInput} from "@mantine/core";
import {useNavigate} from "react-router-dom";
import {useRecoilState} from "recoil";
import {userState} from "../../states/userState";
import {useForm} from "react-hook-form";
import customStyles from "../../styles/customStyle";
import {useState} from "react";
import {ISignup} from "../../types/IUser";

function SignupForm(props: { isHost: boolean }) {
    const navigate = useNavigate();
    const [userStateValue, setuserStateValue] = useRecoilState(userState);

    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    const nameRegEX = /^[가-힣]{2,}$/;
    const {register, handleSubmit, getValues, watch, formState: {errors}} = useForm<ISignup>({mode: "onChange"});

    const emailInputValue = watch("email");
    const [isEmailValid, setIsEmailValid] = useState(false);

    const onSubmit = (data: ISignup) => {
        if (isEmailValid) {
            setuserStateValue({
                ...data,
                ...props
            });
            navigate("/");
        } else {
            alert("중복 확인 필수");
        }
    };

    const onEmailValid = () => {
        console.log(emailInputValue);

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
                                message: "이메일 형식이 올바르지 않습니다",
                            }
                        })}
                                   placeholder="이메일"
                                   label="이메일"
                                   error={errors.email && errors.email?.message}
                                   className={errors.email ? classes["input-error"] : classes["input"]}
                        />
                    </Grid.Col>
                    <Grid.Col span={"content"}>
                        <Button className={classes["btn-primary"]} style={{marginTop: "24px"}}
                                onClick={onEmailValid}>중복확인</Button>
                    </Grid.Col>
                </Grid>
                {/* </Flex> */}
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
                    className={errors.password ? classes["input-error"] : classes["input"]}/>
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
                           className={errors.passwordConfirm ? classes["input-error"] : classes["input"]}/>
                <TextInput {...register("name", {
                    required: "이름을 입력해주세요",
                    pattern: {
                        value: nameRegEX,
                        message: "이름 형식이 올바르지 않습니다",
                    }
                })}
                           placeholder="이름"
                           label="이름"
                           error={errors.name && errors.name?.message}
                           className={errors.name ? classes["input-error"] : classes["input"]}/>
                <TextInput {...register("phone", {
                    required: "휴대폰 번호를 입력해주세요",
                    minLength: {
                        value: 11,
                        message: "11자 입력해주세요",
                    },
                    maxLength: {
                        value: 11,
                        message: "11자 입력해주세요",
                    },
                })}
                           placeholder="휴대폰 번호"
                           label="휴대폰 번호"
                           error={errors.phone && errors.phone?.message}
                           className={errors.phone ? classes["input-error"] : classes["input"]}
                           type="number"/>
                <TextInput {...register("address", {
                    required: "주소를 입력해주세요"
                })}
                           placeholder="주소"
                           label="주소"
                           error={errors.address && errors.address?.message}
                           className={errors.address ? classes["input-error"] : classes["input"]}/>
                <Button type="submit" className={classes["btn-primary"]}>
                    회원가입
                </Button>
            </Stack>
        </form>
    );
}

export default SignupForm;