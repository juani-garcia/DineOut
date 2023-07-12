export class Shift {
  static readonly MORNING = new Shift("MORNING", "Shift.morning", 8, 12);
  static readonly NOON = new Shift("NOON", "Shift.noon", 12, 16);
  static readonly AFTERNOON = new Shift("AFTERNOON", "Shift.afternoon", 16, 20);
  static readonly EVENING = new Shift("EVENING", "Shift.evening", 20, 24);

  private constructor(
    public readonly name: string,
    public readonly description: string,
    public readonly startingHour: number,
    public readonly closingHour: number
  ) {}
}
