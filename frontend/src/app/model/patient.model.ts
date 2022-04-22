export class Patient {
    id: number;
    name: string;
    surname: string;
    age: number;

    constructor(id: number, name: string, surname: string, age: number) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}