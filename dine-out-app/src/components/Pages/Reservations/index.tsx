import React, { useEffect, useState } from 'react'
import {
  PastToggle,
  ReservationCardContainer,
  ReservationCardHolder,
  ReservationMainContainer,
  ReservationsContainer,
  ReservationTitle,
  ShowPreviousContainer
} from '@/components/Pages/Reservations/styles'
import { useNavigate, useSearchParams } from 'react-router-dom'
import type Reservation from '@/types/models/Reservation'
import { useReservations } from '@/hooks/Reservations/useReservations'
import { useAuth } from '@/hooks/auth/useAuth'
import { HttpStatusCode } from 'axios'
import Error from '@/components/Pages/Error'

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

      console.log(isLoading)
      console.log(reservationList)
      console.log(totalPages)
    }).catch((e) => {
      console.log(e.response)
    })
  }, [queryParams])

  if (error !== null) return <Error errorProp={error}/>

  return (
        <ReservationMainContainer>
            <ReservationCardHolder>
                <ReservationCardContainer>
                    <ReservationTitle>
                        Mis Reservas
                    </ReservationTitle>
                    <ShowPreviousContainer>
                        <PastToggle onClick={handlePastToggle}>Mostrar reservas pasadas</PastToggle>
                    </ShowPreviousContainer>
                    <ReservationsContainer>{past ? <>FOUsMBA</> : <>Pumbate</>}</ReservationsContainer>
                </ReservationCardContainer>
            </ReservationCardHolder>
        </ReservationMainContainer>
  )
}

export default Reservations
