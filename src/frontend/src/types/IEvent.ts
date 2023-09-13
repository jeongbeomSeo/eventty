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
    name: string;
    price: number;
    quantity: number;
    eventId: number;
}

export interface IEventTicketDetail extends IEventTicket{
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