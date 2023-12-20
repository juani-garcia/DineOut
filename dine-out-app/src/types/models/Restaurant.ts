export default interface Restaurant {
  id: number
  address: string
  detail: string
  favCount: number
  mail: string
  name: string
  lat: string
  lng: string
  ownerId: number
  owner: string
  rating: number
  ratingCount: number
  self: string
  zone: string
  image: string
  shifts: string[]
  categories: string[]
  menuSections: string
  menuSectionsOrder: string[]
  reviews: string
}
