import React from 'react'
import type Restaurant from '@/types/models/Restaurant'
import {
  ButtonsContainer,
  CategoriesContainer,
  CategoriesHolder,
  Category as CategoryChip,
  Detail,
  DetailContainer,
  DetailIconContainer,
  Rating,
  RatingContainer,
  RestaurantBlackBoxContainer,
  RestaurantDetail,
  RestaurantHeader,
  RestaurantTitle,
  Shift as ShiftChip,
  ShiftsContainer,
  ShiftsTitle,
  WhiteButton as Button,
  Zone,
  ZoneContainer
} from './styles'
import { CardImage, CardImageContainer } from '@/components/Elements/RestaurantCard/styles' // TODO
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import Category from '@/types/enums/Category'
import { Shift } from '@/types/enums/Shift'
import { t } from 'i18next'
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu'
import { Link } from 'react-router-dom'

interface RestaurantBigCardProps {
  restaurant: Restaurant
}

export default function RestaurantBigCard ({ restaurant }: RestaurantBigCardProps): JSX.Element {
  return (
        <RestaurantBlackBoxContainer>
            <RestaurantHeader>
                <RestaurantTitle>{restaurant.name}</RestaurantTitle>
                <RatingContainer>
                    <Rating>
                        {[...Array(restaurant.rating)].map((_, index) => (
                            <StarIcon key={index} color="secondary"/>
                        ))}
                        {[...Array(5 - restaurant.rating)].map((_, index) => (
                            <StarBorderIcon key={index} color="primary"/>
                        ))}
                    </Rating>
                </RatingContainer>
            </RestaurantHeader>
            <CardImageContainer className="card-image">
                <CardImage src={restaurant.image}/>
            </CardImageContainer>
            <DetailContainer>
                <Detail>
                    <DetailIconContainer>
                        <RestaurantMenuIcon sx={{ transform: 'scale(4.0)' }} color='info'/>
                    </DetailIconContainer>
                    <RestaurantDetail>{restaurant.detail}</RestaurantDetail>
                </Detail>
            </DetailContainer>
            <ZoneContainer>
                <Zone>&#128205;{restaurant.address}</Zone>
            </ZoneContainer>
            <CategoriesContainer>
                <CategoriesHolder>
                    {
                        restaurant.categories.map(Category.fromName).map(category => (
                          (category != null) &&
                            <CategoryChip key={category.name} as={Link}
                                          to={'/restaurants?category=' + category.name}>{category.description}</CategoryChip>
                        ))
                    }
                </CategoriesHolder>
            </CategoriesContainer>
            <ShiftsContainer>
                <ShiftsTitle>{t('Restaurant.opening-hours')}</ShiftsTitle>
                {
                    restaurant.shifts.map(Shift.fromName).map(shift => (
                      (shift != null) &&
                        <ShiftChip
                            key={shift.name}
                        >
                            {t('Shift.full-description', {
                              description: t(shift.description),
                              startingHour: shift.startingHour,
                              closingHour: shift.closingHour
                            })}
                        </ShiftChip>
                    ))
                }
            </ShiftsContainer>
            <ButtonsContainer>
                <Button as={Link} to={`/restaurant/${restaurant.id}/review`}>
                    {t('Review.prompt')}
                </Button>
                <Button as={Link} to={`/reserve/${restaurant.id}`}>
                    {t('Reservation.prompt')}
                </Button>
            </ButtonsContainer>
        </RestaurantBlackBoxContainer>
  )
}
