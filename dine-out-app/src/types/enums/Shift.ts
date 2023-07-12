export class Shift {
    static readonly MORNING = new Shift('MORNING', 'shifts.morning', 8, 12);
    static readonly NOON = new Shift('NOON', 'shifts.noon', 12, 16);
    static readonly AFTERNOON = new Shift('AFTERNOON', 'shifts.afternoon', 16, 20);
    static readonly EVENING = new Shift('EVENING', 'shifts.evening', 20, 24);

    private constructor(private readonly name: string, private readonly description: string, public readonly startingHour: number, public readonly closingHour: number) {

    }

    toString() {
        return this.description;
    }
    
}