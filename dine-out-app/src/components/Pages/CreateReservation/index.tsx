import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { ReservationWhiteBoxContainer, ReservationForm, Header } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField, InputLabel, Select, MenuItem, ListItemText, SelectChangeEvent } from '@mui/material'
import { useForm, Controller } from 'react-hook-form'
import internal from 'stream'

interface ReservationCreationForm {
    amount: number,
    date: Date,
    hour: Date,
    comments: string
};

const options: string[] = [
    "1",
    "2",
    "3"
];

export default function Reservation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()

  const onSubmit = (data: any) => {
    console.log(data)
  };

  return (
    <MyContainer>
        <Title>{t('Reservation.create.title')}</Title>
        <ReservationWhiteBoxContainer>
            <ReservationForm onSubmit={handleSubmit(onSubmit)}>
                <Header>{t('Reservation.create.header')}</Header>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <FormControl component="form"
                             sx={{
                               '& .MuiButton-root': {
                                 width: '40%'
                               }
                             }}>
                    <TextField
                        label={t('Reservation.create.amount')}
                        fullWidth
                        margin="normal"
                        {...control.register('amount')}
                        type='number'
                        variant="standard"
                    />
                    <TextField
                        label={t('Reservation.create.date')}
                        fullWidth
                        margin="normal"
                        type='date'
                        {...control.register('date')}
                        variant="standard"
                    />
                    <FormControl fullWidth>
                        <Controller
                            name="time"
                            control={control}
                            rules={{ required: true }}
                            defaultValue=""
                            render={({ field }) => (
                            <Select {...field} id="time-select" fullWidth label={t('Reservation.create.time')}>
                                {options.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option}
                                </MenuItem>
                                ))}
                            </Select>
                            )}
                        />
                    </FormControl>
                    <TextField
                        label={t('Register-restaurant.comments')}
                        fullWidth
                        margin="normal"
                        {...control.register('comments')}
                        variant="standard"
                    />
                    <div style={{display: 'flex', justifyContent: 'space-around'}}>
                        <Button variant="contained" color="error">
                            {t('Reservation.cancel')}
                        </Button>
                        <Button type="submit" variant="contained" color="primary">
                            {t('Reservation.submit')}
                        </Button>
                    </div>
                </FormControl>
            </ReservationForm>
        </ReservationWhiteBoxContainer>
    </MyContainer>
  )
}
