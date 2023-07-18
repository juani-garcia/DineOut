export class Shift {
  static values: Shift[] = []

  static getSlotsFromShift(shift: Shift): string[] {
    const slots: string[] = []
    const range = (start: number, end: number) =>
      Array.from(Array(end - start + 1).keys()).map((x) => x + start)
    range(shift.startingHour, shift.closingHour).forEach((hour) => {
      slots.push(`${hour}:00`)
      slots.push(`${hour}:30`)
    })

    return slots
  }

  static getSlotsfromShiftArray(shifts: Shift[]): string[] {
    const slots: string[] = []
    shifts
      .map((shift) => this.getSlotsFromShift(shift))
      .forEach((newSlots) => slots.concat(newSlots))
    return slots
  }

  static readonly MORNING = new Shift('MORNING', 'Shift.morning', 8, 12)
  static readonly NOON = new Shift('NOON', 'Shift.noon', 12, 16)
  static readonly AFTERNOON = new Shift('AFTERNOON', 'Shift.afternoon', 16, 20)
  static readonly EVENING = new Shift('EVENING', 'Shift.evening', 20, 24)

  private constructor(
    public readonly name: string,
    public readonly description: string,
    public readonly startingHour: number,
    public readonly closingHour: number
  ) {
    Shift.values.push(this)
  }

  static fromName(name: string) {
    return Shift.values.find((shift) => shift.name === name)
  }
}
