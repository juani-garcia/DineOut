import { test } from 'vitest'

export class LocalStorageMock {
  private store: any
  readonly length: number = 1
  constructor () {
    this.store = {}
  }

  clear (): void {
    this.store = {}
  }

  key (index: number): string | null {
    return this.store[index]
  }

  getItem (key: string): string {
    return this.store[key] ?? null
  }

  setItem (key: string, value: string): void {
    this.store[key] = String(value)
  }

  removeItem (key: string): void {
    // eslint-disable-next-line @typescript-eslint/no-dynamic-delete
    delete this.store[key]
  }
}

// eslint-disable-next-line no-global-assign
test('', () => {})
