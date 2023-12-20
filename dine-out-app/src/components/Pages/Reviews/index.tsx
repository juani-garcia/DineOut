import React, { useEffect, useState } from 'react'
import {
  ExploreRestaurantsText,
  NoReservationsText,
  ReservationCardContainer,
  ReservationCardHolder,
  ReservationMainContainer,
  ReservationsContainer,
  ReservationTitle
} from '@/components/Pages/Reservations/styles'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import Error from '@/components/Pages/Error'
import { CircularProgress, Divider, Pagination } from '@mui/material'
import { useSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { DineOutHeaders, localPaths, roles } from '@/common/const'
import type Review from '@/types/models/Review'
import { useReviews } from '@/hooks/Reviews/useReviews'
import { Rating, RatingContainer } from '@/components/Elements/RestaurantCard/styles'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import useRestaurantFromUri from '@/hooks/Restaurants/useRestaurantFromUri'
import type Restaurant from '@/types/models/Restaurant'
import { HttpStatusCode } from 'axios'

function Reviews (): JSX.Element {
  const { t } = useTranslation()
  const [reviewList, setReviewList] = useState<Review[]>([])
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { isLoading, reviews } = useReviews()
  const { isLoading: ild, requestRestaurant } = useRestaurantFromUri()
  const { user } = useAuth()
  const { enqueueSnackbar } = useSnackbar()

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: localPaths.REVIEWS }
      })
    } else {
      const existingParams = new URLSearchParams(queryParams.toString())

      if (user?.roles.includes(roles.RESTAURANT)) {
        if (user?.restaurantId === undefined) {
          navigate('/restaurant/register', {
            state: { from: localPaths.REVIEWS }
          })
          return
        }
        setQueryParams({
          page: existingParams.get('page') ?? '1',
          byUser: user?.userId.toString() ?? '',
          forRestaurant: user?.restaurantId.toString() ?? ''
        })
      } else {
        setQueryParams({
          page: existingParams.get('page') ?? '1',
          byUser: user?.userId.toString() ?? ''
        })
      }
    }
  }, [user])

  useEffect(() => {
    if (user?.userId.toString() === queryParams.get('byUser')) {
      reviews(queryParams).then((response) => {
        if (response.status >= 400 && user?.roles.includes(roles.RESTAURANT)) {
          navigate('/restaurant/register', {
            state: { from: localPaths.REVIEWS }
          })
        }

        if (response.status >= 500) {
          setReviewList([])
          navigate('/error?status=' + response.status.toString())
        }

        if (response.status >= 400) {
          setError(response.status)
          return
        }

        setTotalPages(Number(response.headers[DineOutHeaders.TOTAL_PAGES_HEADER]))
        if (response.status === HttpStatusCode.NoContent) {
          const existingParams = new URLSearchParams(queryParams.toString())
          existingParams.set('page', '1')
          setQueryParams(existingParams)
          setReviewList([])
        } else {
          setReviewList(response.data as Review[])
        }
      }).catch(e => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
        console.log(e)
      })
    }
  }, [queryParams])

  useEffect(() => {
    if (reviewList.length > 0) {
      reviewList.forEach((review) => {
        requestRestaurant(review.restaurant).then((response) => {
          if (response.status >= 500) {
            setReviewList([])
            navigate('/error?status=' + response.status.toString())
          }

          review.restaurantObj = response.data as Restaurant
        }).catch(e => {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
          console.log(e)
        })
      })
    }
  }, [reviewList])

  if (error !== null) return <Error errorProp={error}/>

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  return (
        <ReservationMainContainer>
            <ReservationCardHolder>
                <ReservationCardContainer>
                    <ReservationTitle>
                        Reviews
                    </ReservationTitle>
                    <ReservationsContainer>
                        {isLoading || ild
                          ? (
                                <CircularProgress color="secondary" size="100px"/>
                            )
                          : (
                              reviewList.length === 0
                                ? (
                                        <>
                                            <NoReservationsText>
                                              {t('Reviews.empty')}
                                            </NoReservationsText>
                                          {((user?.roles.includes(roles.DINER)) === true)
                                            ? (
                                            <ExploreRestaurantsText as={Link} to={localPaths.RESTAURANTS}>
                                                ¡Explorá los mejores restaurantes!
                                            </ExploreRestaurantsText>
                                              )
                                            : (
                                                  <></>
                                              )
                                          }
                                        </>
                                  )
                                : (
                                        <>
                                            {
                                                reviewList.map((review: Review) => (
                                                    <>
                                                      <RatingContainer>
                                                        <Rating>
                                                          {[...Array(review.rating)].map((_, index) => (
                                                              <StarIcon key={index} color="secondary"/>
                                                          ))}
                                                          {[...Array(5 - review.rating)].map((_, index) => (
                                                              <StarBorderIcon key={index} color="primary"/>
                                                          ))}
                                                        </Rating>
                                                      </RatingContainer>
                                                      <h1>{review.restaurantObj?.id}</h1>
                                                      <h1>{review.restaurantObj?.name}</h1>
                                                      <h1>{review.restaurantObj?.rating}</h1>
                                                      <h1>{review.review}</h1>
                                                      <Divider/>
                                                    </>
                                                ))
                                            }

                                            {
                                                totalPages > 1
                                                  ? (
                                                        <Pagination
                                                            count={totalPages}
                                                            page={Number((queryParams.get('page') !== '') ? queryParams.get('page') : 1)}
                                                            onChange={handlePageChange}
                                                            size="large"
                                                            showFirstButton
                                                            showLastButton
                                                            siblingCount={3}
                                                            boundaryCount={3}
                                                            sx={{
                                                              '& .MuiPaginationItem-root': {
                                                                color: '#000000'
                                                              }
                                                            }}
                                                        />
                                                    )
                                                  : (
                                                        <></>
                                                    )
                                            }
                                        </>
                                  )
                            )
                        }
                    </ReservationsContainer>
                </ReservationCardContainer>
            </ReservationCardHolder>
        </ReservationMainContainer>
  )
}

export default Reviews
