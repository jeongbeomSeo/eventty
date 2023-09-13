export interface ISignup {
    email: string;
    password: string;
    passwordConfirm?: string;
    name: string;
    phone: string;
    birth?: Date;
    address?: string;
}

export interface ILogin {
    email: string;
    password: string;
}

export interface IUser {
    userId: number;
    name: string;
    address: string;
    birth: Date;
    phone: string;
}

export interface IMyInfo {
    name: string;
    phone: string;
    address: string;
}

export interface IChangePW {
    currentPassword: string;
    newPassword: string;
}