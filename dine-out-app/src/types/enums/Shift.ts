export class Shift {
  static readonly MORNING = new Shift('MORNING', 'Shifts.morning', 8, 12)
  static readonly NOON = new Shift('NOON', 'Shifts.noon', 12, 16)
  static readonly AFTERNOON = new Shift('AFTERNOON', 'Shifts.afternoon', 16, 20)
  static readonly EVENING = new Shift('EVENING', 'Shifts.evening', 20, 24)

  private constructor (private readonly name: string, private readonly description: string, public readonly startingHour: number, public readonly closingHour: number) {

  }

  toString (): string {
    return this.description
  }
}
