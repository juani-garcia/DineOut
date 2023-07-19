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
  Category as CategoryChip,
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
import { useNavigate } from 'react-router-dom'
import Category from '@/types/enums/Category'
import { useTranslation } from 'react-i18next'

interface RestaurantCardProps {
  restaurant: Restaurant
}

function RestaurantCard ({ restaurant }: RestaurantCardProps): JSX.Element {
  if (restaurant == null) return (<></>)
  let restaurantZoneName = null
  if (restaurant.zone !== null && restaurant.zone !== undefined) {
    restaurantZoneName = Zone.values.find(otherZ => otherZ.name === restaurant.zone)?.description
  }
  const { t } = useTranslation()

  const navigate = useNavigate()

  const handleGoToRestaurantView = (): void => {
    navigate('/restaurant/' + restaurant.id.toString() + '/view', { state: { restaurant } })
  }

  const MAX_RATING = 5
  const secondaryColorStars = restaurant.rating
  const primaryColorStars = MAX_RATING - restaurant.rating

  return (
        <RestaurantWhiteBoxContainer onClick={handleGoToRestaurantView}>
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
                            {
                                restaurant.categories.map(Category.fromName).map(category => (
                                  (category != null) &&
                                    <CategoryChip key={category.name}>{t(category.description)}</CategoryChip>
                                ))
                            }
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
