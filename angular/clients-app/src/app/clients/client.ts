export class Client {

    id: number;
    name: string;
    surname: string;
    creationDate: string;
    email: string;

    constructor(id: number, name: string, surname: string, creationDate: string, email: string) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.creationDate = creationDate;
        this.email = email;
    }
}