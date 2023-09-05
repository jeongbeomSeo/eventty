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

export interface IEventWrite {
    id: number;
    hostId: number;
    title: string;
    ticket: IEventTicket[];
    eventStartAt: Date;
    eventEndAt: Date;
    applyStartAt: Date;
    applyEndAt: Date;
    participateNum: number;
    content: string;
    location: string;
    category: string;
    isActive: boolean;
    isDeleted: boolean;
}

export interface IEventTicket {
    id: number;
    title: string;
    price: number;
    limit: number;
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