import React from 'react'
import {
  Address,
  CardContent,
  CardImage,
  CardImageContainer,
  CategoriesAndRating,
  CategoriesContainer,
  CategoriesHolder,
  CategoriesTitle,
  Category,
  Detail,
  Name,
  NameAndZone,
  Rating,
  RatingContainer,
  RatingCount,
  RestaurantWhiteBoxContainer,
  ZoneContainer
} from '@/components/Elements/RestaurantCard/styles'
import type Restaurant from '@/types/models/Restaurant'
import { Zone } from '@/types/enums/Zone'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'

interface RestaurantCardProps {
  restaurant: Restaurant
}

function RestaurantCard ({ restaurant }: RestaurantCardProps): JSX.Element {
  if (restaurant == null) return (<></>)
  let restaurantZoneName = null
  if (restaurant.zone !== null && restaurant.zone !== undefined) {
    restaurantZoneName = Zone.values.find(otherZ => otherZ.name === restaurant.zone)?.description
  }

  const MAX_RATING = 5
  const secondaryColorStars = restaurant.rating
  const primaryColorStars = MAX_RATING - restaurant.rating

  return (
        <RestaurantWhiteBoxContainer>
            <CardImageContainer className="card-image">
                <CardImage src={'http://pawserver.it.itba.edu.ar/paw-2022a-10/image/1'}/>
            </CardImageContainer>
            <CardContent className="card-content">
                {/* Title / zone */}
                <NameAndZone>
                    <Name className={'restaurant-name'}>{restaurant.name}</Name>
                    {restaurantZoneName === null
                      ? <></>
                      : <ZoneContainer className={'restaurant-zone'}>&#128205;{restaurantZoneName}</ZoneContainer>
                    }
                </NameAndZone>
                {/* detail */}
                <Detail>{restaurant.detail}</Detail>
                {/* address */}
                <Address>{restaurant.address}</Address>
                {/* categories list / rating & rating amount */}
                <CategoriesAndRating>
                    <CategoriesContainer>
                        <CategoriesTitle>Categories:</CategoriesTitle>
                        <CategoriesHolder>
                            <Category>Pumabaatae</Category>
                            <Category>Piuamba</Category>
                            <Category>Fiuamba</Category>
                        </CategoriesHolder>
                    </CategoriesContainer>
                    <RatingContainer>
                        <Rating>
                            {[...Array(secondaryColorStars)].map((_, index) => (
                                <StarIcon key={index} color="secondary"/>
                            ))}
                            {[...Array(primaryColorStars)].map((_, index) => (
                                <StarBorderIcon key={index} color="primary"/>
                            ))}
                        </Rating>
                        <RatingCount>({restaurant.ratingCount})</RatingCount>
                    </RatingContainer>
                </CategoriesAndRating>
            </CardContent>
        </RestaurantWhiteBoxContainer>
  )
}

export default RestaurantCard
