import { createGlobalStyle } from "styled-components";

const globalStyle = createGlobalStyle`
    @font-face {
        font-family: "NanumSquareNeo-Variable";
        src: url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.eot?#iefix`}) format("embedded-opentype"),
        url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff2`}) format("font-woff2"),
        url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff`}) format("woff");
    }
    * {
        font-family: "NanumSquareNeo-Variable" !important;
        text-decoration: none;
        color: inherit;
    }
    :root {
        --primary: #7536DC;
    }
`;

export default globalStyle;