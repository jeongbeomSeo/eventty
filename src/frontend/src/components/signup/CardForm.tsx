import { Container, Grid, Card, Flex, Title } from "@mantine/core";
import { ICard } from "../../types/ICard";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { cardTitleState } from "../../states/cardTitleState";
import Logo from "../common/Logo";

function CardForm({ children }: ICard) {
    const cardTitleValue = useRecoilValue(cardTitleState);

    return (
        <Container style={{ marginTop: "10vh" }}>
            <Flex justify={"center"}>
                <Link to={"/"}>
                    <Logo fill={"var(--primary)"} height={"2rem"}/>
                </Link>
            </Flex>
            <Grid>
                <Grid.Col span={"auto"} />
                <Grid.Col xs={8} sm={6} md={5} >
                    <Card padding={"1rem"}>
                        <Title size={"h2"} align="center" style={{ marginBottom: "2rem" }}>{cardTitleValue}</Title>
                        {children}
                    </Card>
                </Grid.Col>
                <Grid.Col span={"auto"} />
            </Grid>
        </Container>
    )
}

export default CardForm;