export interface IEvent {
    id: number;
    hostId: number;
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
    eventStartAt: Date;
    eventEndAt: Date;
    location: string;
    category: string;
    content: string;
    applyStartAt: Date;
    applyEndAt: Date;
    tickets: IEventTicket[];
    image: File;
}

export interface IEventTicket {
    name: string;
    price: number;
    quantity: number;
}

export interface IEventTicketDetail extends IEventTicket{
    id: number;
    eventId: number;
    appliedTicketCount: number;
}

export interface IEventDetail{
    id: number;
    hostId: number;
    hostName: string;
    hostPhone: string;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    categoryName: string;
    isActive: boolean;
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

export interface IEventMain {
    Top10CreatedAt: IEvent[],
    Top10ApplyEndAt: IEvent[],
    Top10Views: IEvent[],
}