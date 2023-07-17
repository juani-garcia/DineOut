import { MyContainer } from '@/components/Elements/utils/styles'
import React from 'react'
import { useTranslation } from 'react-i18next'
import { Header, ReservationForm, ReservationWhiteBoxContainer } from './styles'
import { Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useCreateReservation } from '@/hooks/Reservations/useCreateReservation'
import { paths } from '@/common/const'
import { useParams } from 'react-router-dom'

// interface ReservationCreationForm {
//     amount: number,
//     date: Date,
//     hour: Date,
//     comments: string
// };

const options: string[] = [ // TODO: Get from restaurant shifts.
  '1',
  '2',
  '3'
]

const getCurrentDate = (): string => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

export default function Reservation (): JSX.Element {
  const { t } = useTranslation()
  const { register, handleSubmit } = useForm()
  const { isLoading, createReservation } = useCreateReservation()
  const params = useParams()

  if (params.id === undefined) return <>te mandaste un re moco</> // TODO: Redirect to 404

  const onSubmit = (data: any): void => {
    createReservation(paths.API_URL + paths.RESERVATION, parseInt(params.id as string), data.comments, data.amount, data.date.toString(), data.time).then((response) => {
      console.log(response)
    }).catch((e) => {
      console.log(e)
    })
    console.log(data)
    console.log(isLoading)
  }

  return (
        <MyContainer>
            <ReservationWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <ReservationForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('Reservation.title')}</Header>
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
                            <Select {...register('zone')}
                                    labelId="timeLabel"
                                    defaultValue={''}
                                    id="timeSelect"
                                    fullWidth={true}
                                    label={t('Reservation.time')}
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                {options.map((option) => (
                                    <MenuItem key={option} value={option}>
                                        {option}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <TextField
                            label={t('Reservation.comments')}
                            fullWidth
                            margin="normal"
                            {...register('comments')}
                        />
                        <div style={{ display: 'flex', justifyContent: 'space-around' }}>
                            <Button color="secondary">
                                {t('Reservation.cancel')}
                            </Button>
                            <Button type="submit" color="primary" sx={{ fontWeight: '500' }}>
                                {t('Reservation.submit')}
                            </Button>
                        </div>
                    </FormControl>
                </ReservationForm>
            </ReservationWhiteBoxContainer>
        </MyContainer>
  )
}
