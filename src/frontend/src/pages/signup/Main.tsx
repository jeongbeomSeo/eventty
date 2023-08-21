import { Button, Stack } from "@mantine/core";
import { Link } from "react-router-dom";
import GoogleBtn from "../../components/signup/GoogleBtn";
import { useSetRecoilState } from 'recoil';
import { cardTitleState } from '../../states/cardTitleState';
import { useEffect } from 'react';
import customStyle from "../../styles/customStyle";

function SignupMain() {
    const { classes } = customStyle();

    const setCardTitleState = useSetRecoilState(cardTitleState);

    useEffect(() => {
        setCardTitleState("회원가입");
    }, []);

    return (
        <Stack>
            <Button className={classes["btn-primary"]} component={Link} to={"member"}>
                개인 회원가입
            </Button>
            <Button className={classes["btn-gray-outline"]} component={Link} to={"host"}>
                주최자 회원가입
            </Button>

            <p style={{ textAlign: "center", fontSize: "0.8rem" }}>
                또는<br />SNS 가입하기
            </p>

            <GoogleBtn>Google 계정 가입</GoogleBtn>
        </Stack>
    )
}

export default SignupMain;