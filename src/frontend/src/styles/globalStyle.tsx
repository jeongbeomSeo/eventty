import { createGlobalStyle } from "styled-components";

const globalStyle = createGlobalStyle`
  @font-face {
    font-family: "NanumSquareNeo-Variable";
    font-display: swap;
    src: local("NanumSquareNeo-Variable"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff2`}) format("woff2"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff`}) format("woff"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.eot?#iefix`}) format("embedded-opentype");
  }

  * {
    font-family: "NanumSquareNeo-Variable" !important;
    text-decoration: none;
    color: inherit;
  }

  :root {
    --primary: #7536DC;
  }

  // TOAST UI Editor 모바일 환경 시, 툴바 overflow 문제 대응
  .toastui-editor-dropdown-toolbar {
    max-width: none;
    flex-wrap: wrap;
    height: fit-content;
  }
`;

export default globalStyle;