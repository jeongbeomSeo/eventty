export const SetCsrfToken = (res:Response) => {
    const csrf = res.headers.get("X-Csrf-Token");
    return (csrf !== null && csrf !== "") && sessionStorage.setItem("X-Csrf-Token", csrf!);
}

export const GetCsrfToken = () => {
    return sessionStorage.getItem("X-Csrf-Token");
}
