import {Stack, Button, TextInput, Flex, Divider} from "@mantine/core";
import CardForm from "../components/signup/CardForm";
import { useForm } from "react-hook-form";
import {Link, useLocation, useNavigate} from "react-router-dom";
import GoogleBtn from "../components/signup/GoogleBtn";
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { cardTitleState } from '../states/cardTitleState';
import {useEffect, useState} from 'react';
import { userState } from '../states/userState';
import { loginState } from '../states/loginState';
import customStyle from "../styles/customStyle";
import {ILogin} from "../types/IUser";
import {postLogin} from "../service/user/fetchUser";

function Login() {
    const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
    const [loading, setLoading] = useState(false);
    const userStateValue = useRecoilValue(userState);

    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    const { register, handleSubmit, formState: { errors } } = useForm<ILogin>({ mode: "onChange" });
    const onSubmit = (data: ILogin) => {
        if (data.email === userStateValue.email && data.password === userStateValue.password) {
            setIsLoggedIn((prev) => !prev);
            alert("로그인 성공");
        } else {
            alert("실패");
        }

        /*try {
            postLogin(data)
                .then(() => {
                    setIsLoggedIn(true);
                })
                .catch((e) => {
                    console.log(e);
                })
                .finally(() => {
                    setLoading(true);
                })
        }catch (e) {
            console.log(e);
        }*/
    };

    const { classes } = customStyle();

    const setcardTitleState = useSetRecoilState(cardTitleState);
    useEffect(() => {
        setcardTitleState("로그인");
    }, []);


    return (
        <CardForm>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Stack>
                    <TextInput {...register("email", {
                        required: "이메일을 입력해주세요",
                        pattern: {
                            value: emailRegEx,
                            message: "이메일 형식이 올바르지 않습니다",
                        }
                    })}
                        placeholder="이메일"
                        error={errors.email && errors.email?.message}
                        className={errors.email ? classes["input-error"] : classes.input}
                    />
                    <TextInput {...register("password", {
                        required: "비밀번호를 입력해주세요",
                    })}
                        type="password"
                        placeholder="비밀번호"
                        error={errors.password && errors.password?.message}
                        className={errors.password ? classes["input-error"] : classes.input}
                    />
                    <Button type="submit" className={classes["btn-primary"]}>로그인</Button>
                    <Flex gap={"xs"} align={"center"} justify={"center"} className={classes["signup-footer"]}>
                        <Link to={"/signup"}>회원가입</Link>|
                        <Link to={""}>계정 찾기</Link>|
                        <Link to={""}>비밀번호 찾기</Link>
                    </Flex>
                    <Divider my={"xs"} labelPosition={"center"} label={"SNS 로그인"}
                             className={classes["signup-divider"]}/>
                    <GoogleBtn>Google 계정 로그인</GoogleBtn>
                </Stack>
            </form>
        </CardForm>
    )
}

export default Login;