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
    address?: string;
    birth?: Date;
    image?: string;
    phone: string;
}

export interface IChangePW {
    currentPassword: string;
    newPassword: string;
    newPasswordConfirm?: string;
}