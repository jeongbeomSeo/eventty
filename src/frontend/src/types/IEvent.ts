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
    userId: number;
    title: string;
    image: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    category: string;
    content: string;
    applyStartAt: Date;
    applyEndAt: Date;
    tickets: IEventTicket[];
}

export interface IEventTicket {
    name: string;
    price: number;
    quantity: number;
}

export interface IEventTicketDetail extends IEventTicket{
    id: number;
    eventId: number;
    is_deleted: boolean;
}

export interface IEventDetail {
    id: number;
    userId: number;
    title: string;
    image: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    categoryName: string;
    isActive: boolean;
    isDeleted: boolean;
    content: string;
    applyStartAt: Date;
    applyEndAt: Date;
    views: number;
    tickets: IEventTicketDetail[];
}