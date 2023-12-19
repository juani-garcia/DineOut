export default interface Reservation {
  id: number
  restaurant: string
  owner: string
  amount: number
  dateTime: string
  comments: string
  isConfirmed: boolean
  self: string
  mail?: string
  ownerFirstName?: string
  ownerLastName?: string
}
