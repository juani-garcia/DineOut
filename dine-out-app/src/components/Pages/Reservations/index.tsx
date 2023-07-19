import React, { useEffect, useState } from 'react'
import {
  ExploreRestaurantsText,
  NoReservationsText,
  PastToggle,
  ReservationCardContainer,
  ReservationCardHolder,
  ReservationInfo,
  ReservationMainContainer,
  ReservationsContainer,
  ReservationTitle,
  ShowPreviousContainer
} from '@/components/Pages/Reservations/styles'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import type Reservation from '@/types/models/Reservation'
import { useReservations } from '@/hooks/Reservations/useReservations'
import { useAuth } from '@/hooks/auth/useAuth'
import { HttpStatusCode } from 'axios'
import Error from '@/components/Pages/Error'
import { CircularProgress, Pagination } from '@mui/material'

function Reservations (): JSX.Element {
  const [past, setPast] = useState<boolean>(JSON.parse(localStorage.getItem('past') ?? 'false') as boolean)
  const [reservationList, setReservationList] = useState<Reservation[]>([])
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { isLoading, reservations } = useReservations()
  const { user } = useAuth()

  const handlePastToggle = (): void => {
    localStorage.setItem('past', JSON.stringify(!past))
    const existingParams = new URLSearchParams(queryParams.toString())
    setQueryParams({
      page: existingParams.get('page') ?? '1',
      userId: user?.userId.toString() ?? '',
      past: (!past ? 'true' : 'false')
    })
    setPast(!past)
  }

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: '/reservations' }
      })
    } else {
      const existingParams = new URLSearchParams(queryParams.toString())

      if (existingParams.get('past') === 'true') {
        setPast(true)
      } else if (existingParams.get('past') === 'false') {
        setPast(false)
      } else if (existingParams.get('past') !== '' && existingParams.get('past') !== null && existingParams.get('past') !== undefined) {
        setError(HttpStatusCode.BadRequest)
        return
      }

      setQueryParams({
        page: existingParams.get('page') ?? '1',
        userId: user?.userId.toString() ?? '',
        past: (past ? 'true' : 'false')
      })
    }
  }, [user])

  useEffect(() => {
    if (user?.userId.toString() === queryParams.get('userId')) {
      reservations(queryParams).then((response) => {
        if (response.status >= 500) {
          setReservationList([])
          navigate('/error?status=' + response.status.toString())
        }

        if (response.status >= 400) {
          setError(response.status)
          return
        }

        setTotalPages(Number(response.headers['x-total-pages']))

        setReservationList(response.data as Reservation[])

        console.log(reservationList)
        console.log(totalPages)
      }).catch((e) => {
        console.log(e.response)
      })
    }
  }, [queryParams])

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
                        Reservas {past ? 'pasadas' : 'próximas'}
                    </ReservationTitle>
                    <ShowPreviousContainer>
                        <PastToggle onClick={handlePastToggle}>Mostrar
                            reservas {past ? 'pasadas' : 'próximas'}</PastToggle>
                    </ShowPreviousContainer>
                    <ReservationsContainer>
                        {isLoading
                          ? (
                                <CircularProgress color="secondary" size="100px"/>
                            )
                          : (
                              reservationList.length === 0
                                ? (
                                        <>
                                            <NoReservationsText>
                                                No hay ninguna reserva
                                            </NoReservationsText>
                                            <ExploreRestaurantsText as={Link} to={'/restaurants?page=1'}>
                                                ¡Explorá los mejores restaurantes!
                                            </ExploreRestaurantsText>
                                        </>
                                  )
                                : (
                                        <>
                                            {
                                                reservationList.map((reservation: Reservation) => (
                                                    <ReservationInfo key={reservation.id}>
                                                        <hr style={{
                                                          boxSizing: 'content-box',
                                                          height: '0',
                                                          overflow: 'visible',
                                                          width: '100%'
                                                        }}/>
                                                        {/* TODO: Style when i have the restaurant name */}
                                                        <div>{reservation.restaurant}</div>
                                                        <div>{reservation.amount}</div>
                                                        <div>{reservation.comments}</div>
                                                        <div>{reservation.dateTime}</div>
                                                        <div>{reservation.isConfirmed}</div>
                                                    </ReservationInfo>
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

export default Reservations
