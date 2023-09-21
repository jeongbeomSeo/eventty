import {getCategoryEvents} from "../service/event/fetchEvent";

export const loader = ({params}:any) => {
    const convertParam = `${params.category.charAt(0).toUpperCase()}${params.category.slice(1)}`;
    console.log(convertParam);

    return getCategoryEvents(convertParam);
}
