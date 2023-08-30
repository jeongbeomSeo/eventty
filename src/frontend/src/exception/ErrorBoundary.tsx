import React, {useEffect, useState} from "react";

function ErrorBoundary() {
    const [hasError, setHasError] = useState(false);
    const [error, setError] = useState(null);
    const [errorInfo, setErrorInfo] = useState(null);

    useEffect(() => {

    }, []);

    return (
        <>

        </>
    );
}

export default ErrorBoundary;