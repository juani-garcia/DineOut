import React, { useEffect, useState } from 'react'
import {
  ExploreRestaurantsText,
  NoReservationsText,
  PastToggle,
  ReservationCardContainer,
  ReservationCardHolder,
  ReservationMainContainer,
  ReservationTitle,
  ShowPreviousContainer,
  ReservationCardsContainer, MySpecialContainerRes, MyVerySpecialContainerRes
} from '@/components/Pages/Reservations/styles'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import type Reservation from '@/types/models/Reservation'
import { useReservations } from '@/hooks/Reservations/useReservations'
import { useAuth } from '@/hooks/auth/useAuth'
import { HttpStatusCode } from 'axios'
import Error from '@/components/Pages/Error'
import { CircularProgress, Pagination } from '@mui/material'
import { useSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { DineOutHeaders, localPaths, roles } from '@/common/const'
import ReservationCard from '@/components/Elements/ReservationCard'

function Reservations (): JSX.Element {
  const { t } = useTranslation()
  const [past, setPast] = useState<boolean>(JSON.parse(localStorage.getItem('past') ?? 'false') as boolean)
  const [reservationList, setReservationList] = useState<Reservation[]>([])
  const [startedLoading, setStartedLoading] = useState<boolean>(false)
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { isLoading, reservations } = useReservations()
  const { user } = useAuth()
  const { enqueueSnackbar } = useSnackbar()

  if (user === null || user === undefined) {
    navigate(-1)
  }

  const handlePastToggle = (): void => {
    localStorage.setItem('past', JSON.stringify(!past))
    const existingParams = new URLSearchParams(queryParams.toString())
    setQueryParams({
      page: existingParams.get('page') ?? '1',
      past: (!past ? 'true' : 'false')
    })
    setPast(!past)
  }

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: localPaths.RESERVATION }
      })
    } else {
      const existingParams = new URLSearchParams(queryParams.toString())

      if (existingParams.get('past') === 'true') {
        setPast(true)
      } else {
        setPast(false)
      }
    }
  }, [user])

  useEffect(() => {
    if (user !== null && user !== undefined) {
      if (user.roles.includes(roles.RESTAURANT) && user.restaurantId === undefined) {
        navigate('/restaurant/register', {
          state: { from: localPaths.REVIEWS }
        })
        return
      }
      const params = new URLSearchParams(queryParams.toString())
      params.set('userId', user.userId.toString())
      reservations(params).then((response) => {
        if (response.status >= 400 && user.roles.includes(roles.RESTAURANT)) {
          navigate('/restaurant/register', {
            state: { from: localPaths.RESERVATION }
          })
        }

        if (response.status >= 500) {
          setReservationList([])
          navigate('/error?status=' + response.status.toString())
        }

        if (response.status >= 400) {
          setError(response.status)
          return
        }

        setTotalPages(Number(response.headers[DineOutHeaders.TOTAL_PAGES_HEADER]))
        if (response.status === HttpStatusCode.NoContent) {
          setReservationList([])
        } else {
          setReservationList(response.data as Reservation[])
        }
      }).catch((e) => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
      setStartedLoading(true)
    }
  }, [queryParams])

  if (error !== null) return <Error errorProp={error}/>

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  const onDeleteReservation = (deleted: Reservation): void => {
    const newList = reservationList.filter(reservation => reservation.id !== deleted.id)
    setReservationList(newList)
  }

  const onConfirmReservation = (confirmed: Reservation): void => {
    const newList = reservationList.map(reservation => {
      if (reservation.id === confirmed.id) reservation.isConfirmed = true
      return reservation
    })
    setReservationList(newList)
  }

  return (
    <MySpecialContainerRes>
      <ReservationMainContainer>
        <ReservationCardHolder>
          <ReservationCardContainer>
            <ReservationTitle>
              {past ? t('Reservation.pastReservations') : t('Reservation.reservations')}
            </ReservationTitle>
            <ShowPreviousContainer>
              <PastToggle onClick={handlePastToggle}>
                {!past ? t('Reservation.showPast') : t('Reservation.showNext')}
              </PastToggle>
            </ShowPreviousContainer>
          </ReservationCardContainer>
        </ReservationCardHolder>
      </ReservationMainContainer>
      <ReservationCardsContainer>
        {isLoading
          ? (
            <CircularProgress color="secondary" size="100px"/>
            )
          : (
              startedLoading && reservationList.length === 0
                ? (
                <ReservationMainContainer>
                  <ReservationCardHolder>
                    <ReservationCardContainer>
                      <NoReservationsText>
                        {t('Reservation.noReservations')}
                      </NoReservationsText>
                      <ExploreRestaurantsText as={Link} to={localPaths.RESTAURANTS}>
                        {t('Restaurant.exploreRestaurants')}
                      </ExploreRestaurantsText>
                    </ReservationCardContainer>
                  </ReservationCardHolder>
                </ReservationMainContainer>
                  )
                : (
                <>
                  <MyVerySpecialContainerRes>
                  {reservationList.map((reservation: Reservation) => (
                    <ReservationCard
                      reservation={reservation}
                      deleteCallback={onDeleteReservation}
                      confirmCallback={onConfirmReservation}
                      key={reservation.id}
                      forUser={user?.roles != null
                        ? user.roles.includes(roles.DINER)
                        : false }/>
                  ))}
                  </MyVerySpecialContainerRes>
                  {totalPages > 1
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
                            color: '#FFFFFF'
                          }
                        }}/>
                      )
                    : (
                      <></>
                      )}
                </>
                  )
            )}
      </ReservationCardsContainer>
    </MySpecialContainerRes>
  )
}

export default Reservations
