export class Shift {
  static values: Shift[] = []

  static getSlotsFromShift (shift: Shift): string[] {
    const slots: string[] = []
    // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
    const range = (start: number, end: number) =>
      Array.from(Array(end - start).keys()).map((x) => x + start)
    range(shift.startingHour, shift.closingHour).forEach((hour) => {
      const prefix = hour < 10 ? '0' : ''
      slots.push(`${prefix}${hour}:00`)
      slots.push(`${prefix}${hour}:30`)
    })

    return slots
  }

  static getSlotsfromShiftArray (shifts: Shift[]): string[] {
    const slots: string[] = []
    shifts
      .map((shift) => this.getSlotsFromShift(shift))
      .forEach((newSlots) => slots.push(...newSlots)) // Use push to add elements to the array
    return slots.sort()
  }

  static readonly MORNING = new Shift('MORNING', 'Shift.morning', 8, 12)
  static readonly NOON = new Shift('NOON', 'Shift.noon', 12, 16)
  static readonly AFTERNOON = new Shift('AFTERNOON', 'Shift.afternoon', 16, 20)
  static readonly EVENING = new Shift('EVENING', 'Shift.evening', 20, 24)

  private constructor (
    public readonly name: string,
    public readonly description: string,
    public readonly startingHour: number,
    public readonly closingHour: number
  ) {
    Shift.values.push(this)
  }

  static fromName (name: string): Shift | undefined {
    return Shift.values.find((shift) => shift.name === name)
  }
}
