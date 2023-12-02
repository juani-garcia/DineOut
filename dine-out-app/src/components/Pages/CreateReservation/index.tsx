import { MyContainer } from '@/components/Elements/utils/styles'
import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, ReservationForm, ReservationWhiteBoxContainer } from './styles'
import { Button, CircularProgress, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useCreateReservation } from '@/hooks/Reservations/useCreateReservation'
import { localPaths, paths, roles } from '@/common/const'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import type Restaurant from '@/types/models/Restaurant'
import useRestaurant from '@/hooks/Restaurants/useRestaurant'
import Error from '@/components/Pages/Error'
import { HttpStatusCode } from 'axios'
import { Shift } from '@/types/enums/Shift'
import { useSnackbar } from 'notistack'

// interface ReservationCreationForm {
//     amount: number,
//     date: Date,
//     hour: Date,
//     comments: string
// };

const getCurrentDate = (): string => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

interface ReservationProps {
  restaurant?: Restaurant
}

export default function Reservation ({ restaurant: restaurantProp }: ReservationProps): JSX.Element {
  const { t } = useTranslation()
  const { register, handleSubmit } = useForm()
  const { isLoading, createReservation } = useCreateReservation()
  const { isLoading: isLoadingRestaurant, restaurant: requestRestaurant } = useRestaurant()
  const [error, setError] = useState<number | null>(null)
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const params = useParams()
  const { user } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const { enqueueSnackbar } = useSnackbar()

  if (user?.roles.includes(roles.DINER) === false) return <Error errorProp={HttpStatusCode.Forbidden}/>

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
        state: { from: localPaths.RESERVE + '/' + (params.id === undefined ? '' : params.id.toString()) }
      })
    }
  }, [user])

  useEffect(() => {
    if (restaurant != null && restaurant.id === parseInt(params.id as string)) {
      return
    } else if (location.state?.restaurant !== undefined && location.state?.restaurant != null && location.state?.restaurant.id === parseInt(params.id as string)) {
      setRestaurant(location.state.restaurant)
      return
    } else if (restaurantProp?.id !== undefined && restaurantProp.id === parseInt(params.id as string)) {
      setRestaurant(restaurantProp)
      return
    }
    requestRestaurant(Number(params.id)).then(response => {
      if (response.status === 404) {
        setRestaurant(undefined)
        setError(404)
        return
      }
      setRestaurant(response.data as Restaurant)
    }).catch(e => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
    })
  }, [restaurant, params])

  if (error !== null) return <Error errorProp={error}/>

  const handleCancel = (): void => {
    navigate('/restaurant/' + (params.id as string) + '/view')
  }

  const onSubmit = (data: any): void => {
    createReservation(paths.API_URL + paths.RESERVATION, parseInt(params.id as string), data.comments, data.amount, data.date.toString(), data.time).then((response) => {
      if (response.status >= 200 && response.status <= 300) {
        navigate(-1)
      } else {
        enqueueSnackbar(t('Errors.tryAgain'), {
          variant: 'warning'
        })
      }
    }).catch((e) => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
    })
  }

  const restaurantShifts = restaurant?.shifts.map(Shift.fromName).filter((shift) => shift) as Shift[]
  if (restaurantShifts === undefined) return <></>

  const shiftSlots = Shift.getSlotsfromShiftArray(restaurantShifts)

  return (
        <MyContainer>
            <ReservationWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <ReservationForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('Reservation.title')}</Header>
                    <>{
                        isLoadingRestaurant || restaurant === null
                          ? (
                                <CircularProgress color="secondary" size="100px"/>
                            )
                          : (
                                <>
                                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                                    <FormControl component="form"
                                                 sx={{
                                                   '& .MuiButton-root': {
                                                     width: '40%'
                                                   }
                                                 }}>
                                        <TextField
                                            label={t('Reservation.amount')}
                                            fullWidth
                                            margin="normal"
                                            {...register('amount')}
                                            type='number'
                                        />
                                        <TextField
                                            label={t('Reservation.date')}
                                            fullWidth
                                            margin="normal"
                                            type='date'
                                            defaultValue={getCurrentDate()}
                                            {...register('date')}
                                        />
                                        <FormControl sx={{ minWidth: '100%', marginTop: '20px' }}>
                                            <InputLabel id="timeLabel">{t('Reservation.time')}</InputLabel>
                                            <Select {...register('time')}
                                                    labelId="timeLabel"
                                                    defaultValue={''}
                                                    id="timeSelect"
                                                    fullWidth={true}
                                                    label={t('Reservation.time')}
                                            >
                                                {
                                                    shiftSlots.map(shiftSlot => (
                                                        <MenuItem key={shiftSlot}
                                                                  value={shiftSlot}>{shiftSlot}</MenuItem>

                                                    ))
                                                }
                                            </Select>
                                        </FormControl>
                                        <TextField
                                            label={t('Reservation.comments')}
                                            fullWidth
                                            margin="normal"
                                            {...register('comments')}
                                        />
                                        <div style={{ display: 'flex', justifyContent: 'space-around' }}>
                                            <Button color="secondary" onClick={handleCancel}>
                                                {t('Reservation.cancel')}
                                            </Button>
                                            <Button type="submit" color="primary" sx={{ fontWeight: '500' }}>
                                                {
                                                    isLoading
                                                      ? (
                                                            <CircularProgress color="secondary" size="30px"/>
                                                        )
                                                      : (
                                                            <>{t('Reservation.submit')}</>
                                                        )
                                                }
                                            </Button>
                                        </div>
                                    </FormControl>
                                </>
                            )
                    }
                    </>
                </ReservationForm>
            </ReservationWhiteBoxContainer>
        </MyContainer>
  )
}
