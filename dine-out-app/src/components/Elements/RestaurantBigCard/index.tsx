import React, { useState } from 'react'
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
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import { localPaths, roles } from '@/common/const'
import IconButton from '@mui/material/IconButton'
import EditIcon from '@mui/icons-material/Edit'
import { Input } from '@mui/material'
import useImage from '@/hooks/Images/useImage'
import DeleteIcon from '@mui/icons-material/Delete'

interface RestaurantBigCardProps {
  restaurant: Restaurant
}

export default function RestaurantBigCard ({ restaurant }: RestaurantBigCardProps): JSX.Element {
  const { user } = useAuth()
  const navigate = useNavigate()
  const isOwner = user?.restaurantId === restaurant.id
  const [imagePreview, setImagePreview] = useState<string | undefined>(restaurant?.image)
  const { updateImage, deleteImage } = useImage()

  const handleUpdate: React.ChangeEventHandler<HTMLInputElement> = event => {
    if (event.target.files == null) {
      return
    }
    const newImage = event.target.files[0]
    updateImage(`${restaurant.self}/image`, newImage).then(response => {
      if (response.status !== 200) {
        return
      }
      setImagePreview(URL.createObjectURL(newImage))
    }).catch(e => {
      console.error(e.response) // TODO: Toast
    })
  }

  const handleDeletion: React.MouseEventHandler<HTMLButtonElement> = event => {
    deleteImage(`${restaurant.self}/image`).then(response => {
      if (response.status !== 200) {
        return
      }
      setImagePreview(undefined)
    }).catch(e => {
      console.error(e.response)
    })
  }

  return (
        <RestaurantBlackBoxContainer>
            <RestaurantHeader>
                <RestaurantTitle>
                    {restaurant.name}
                    {isOwner && <Link to={'/restaurant/edit'}><IconButton color='secondary' size='large'
                                                                          aria-label='edit'><EditIcon/></IconButton></Link>}
                </RestaurantTitle>
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
                {(imagePreview != null) && (
                    <CardImage src={imagePreview}/>
                )}
                {isOwner &&
                    <div>
                        <Input type='file' name='image' onChange={handleUpdate}/>
                        <IconButton onClick={handleDeletion} color="secondary" aria-label="delete">
                            <DeleteIcon/>
                        </IconButton>
                    </div>
                }
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
                                          to={localPaths.RESTAURANTS + '?category=' + category.name}>{category.description}</CategoryChip>
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
                {
                    ((user?.roles.includes(roles.DINER)) === true)
                      ? (
                            <>
                                <Button as={Link} to={`/restaurant/${restaurant.id}/review`}>
                                    {t('Reviews.prompt')}
                                </Button>
                                <Button as={Link} to={`/reserve/${restaurant.id}`} style={{ marginLeft: 'auto' }}>
                                    {t('Reservation.prompt')}
                                </Button>
                            </>
                        )
                      : (
                            <>
                                {
                                    ((user?.roles.includes(roles.RESTAURANT)) === false)
                                      ? (
                                            <>
                                                <Button onClick={() => {
                                                  navigate('/register', {
                                                    state: { from: window.location.pathname.replace('/paw-2022a-10', '') + window.location.search }
                                                  })
                                                }}>
                                                    {t('registerToReserve')}
                                                </Button>
                                            </>
                                        )
                                      : (<></>)
                                }
                            </>
                        )
                }
            </ButtonsContainer>
        </RestaurantBlackBoxContainer>
  )
}
