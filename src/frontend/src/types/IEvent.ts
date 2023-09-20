export interface IEvent {
    id: number;
    userId: number;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    categoryName: string;
    isActive: boolean;
    isDeleted: boolean;
    image: string;
    originFileName: string;
}

export interface IEventWrite {
    [key:string]: string|any;
    userId: number;
    title: string;
    image: File;
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
    image: string;
    originFileName: string;
}

export interface IEventBooking {
    userId: number;
}