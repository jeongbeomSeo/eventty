export interface IEvent {
    id: number;
    hostId: number;
    title: string;
    image: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    category: string;
    isActive: boolean;
    isDeleted: boolean;
}

export interface IEventDetail {
    eventDetailResponseDTO: {
        id: number;
        content: string;
        views: number;
        applyStartAt: number;
        applyEndAt: number;
        createDate: number;
        updateDate: number;
        deleteDate: number;
    },
    eventResponseDTO: IEvent,
}