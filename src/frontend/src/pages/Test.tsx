import {Container} from "@mantine/core";
import {useForm} from "react-hook-form";

interface Interface {
    name: string;
    age: number;
    color: string;
}

function Test() {

    const {register, handleSubmit, getValues, setValue, watch} = useForm<Interface>();

    return (
        <Container>
            <div style={{padding: "10rem"}}>
                {/*<p>watch: {watch("name")}</p>
                <input {...register("name")}/>*/}

                {/*<p>setValue: {getValues("color")}</p>*/}
                <p>setValue: {watch("color")}</p>
                <button onClick={() => setValue("color", "red")}>red로 변경</button>
            </div>
        </Container>
    )
}

export default Test;