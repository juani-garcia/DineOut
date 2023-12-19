import type Restaurant from '@/types/models/Restaurant'

export default interface Review {
  id: number
  review: string
  rating: number
  restaurant: string
  user: string
  restaurantObj: Restaurant
}
