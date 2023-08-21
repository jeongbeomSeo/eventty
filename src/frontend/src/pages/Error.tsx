import {Container, Stack, Title} from '@mantine/core';
import { Link } from 'react-router-dom';

export function Error() {
    return (
        <Container>
            <Stack align={"center"}>
                <Link to={"/"}>
                    <img src={`${process.env.PUBLIC_URL}/images/404.png`} />
                </Link>
                <Title>404 Not Found</Title>
            </Stack>
        </Container>
    );
}

export default Error;