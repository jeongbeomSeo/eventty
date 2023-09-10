export interface ISignup {
    email: string;
    password: string;
    passwordConfirm: string;
    name: string;
    phone: string;
    nickname: string;
    birth: Date;
    address: string;
}

export interface ILogin {
    email: string;
    password: string;
}

export interface IUser {
    email: string;
    password: string;
    name: string;
    isHost: boolean;
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