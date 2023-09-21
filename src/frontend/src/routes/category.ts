import {getCategoryEvents} from "../service/event/fetchEvent";

export const loader = ({params}:any) => {
    const category = `${params.category.charAt(0).toUpperCase()}${params.category.slice(1)}`;
    return getCategoryEvents(category);
}
