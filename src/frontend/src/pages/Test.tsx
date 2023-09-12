import {SearchRecentHistory} from "../util/SearchRecentHistory";


function Test() {
    const {keywords} = SearchRecentHistory();
    return (
        <>
            {keywords};
        </>
    )
}

export default Test;