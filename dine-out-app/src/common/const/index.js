export const paths = {
  API_URL: 'http://' + process.env.REACT_APP_BASE_URL,
  USERS: 'http://' + process.env.REACT_APP_BASE_URL + '/users',
  RESTAURANTS: '/restaurants',
  RESERVATION: '/reservations',
  REVIEWS: '/reviews'
}

export const localPaths = {
  RESTAURANTS: '/restaurant',
  RESERVATION: '/reservation',
  REVIEWS: '/review'
}

export const roles = {
  ADMIN: 'ADMIN',
  RESTAURANT: 'RESTAURANT',
  DINER: 'DINER',
  BASIC_USER: 'BASIC_USER'
}
