import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, RegisterRestaurantForm, RegisterRestaurantWhiteBoxContainer } from './styles'
import {
  Button,
  Checkbox,
  FormControl,
  InputLabel,
  ListItemText,
  MenuItem,
  Select,
  type SelectChangeEvent,
  TextField
} from '@mui/material'
import { useForm } from 'react-hook-form'
import Category from '@/types/enums/Category'
import { Shift } from '@/types/enums/Shift'

// interface RegisterRestaurantFormInput {
//     name: string,
//     image?: string,
//     address: string,
//     email: string,
//     detail: string,
//     categories: Category[],
//     shifts: Shift[]
// }

export default function RegisterRestaurant (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const [selectedCategories, setselectedCategories] = useState<string[]>([])
  const [selectedShifts, setSelectedShifts] = useState<string[]>([])
  const handleCategoryChange = (event: SelectChangeEvent<typeof selectedCategories>): void => {
    const {
      target: { value }
    } = event
    setselectedCategories(
      typeof value === 'string' ? value.split(',') : value
    )
  }
  const handleShiftChange = (event: SelectChangeEvent<typeof selectedCategories>): void => {
    const {
      target: { value }
    } = event
    setSelectedShifts(
      typeof value === 'string' ? value.split(',') : value
    )
  }

  const onSubmit = (data: any): void => {
    console.log(data)
  }

  return (
        <MyContainer>
            <Title>{t('Register-restaurant')}</Title>
            <RegisterRestaurantWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <RegisterRestaurantForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('Register-restaurant.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form"
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <TextField
                            label={t('Register-restaurant.name')}
                            fullWidth
                            margin="normal"
                            {...control.register('name')}
                            variant="standard"
                        />
                        <TextField
                            label={t('Register-restaurant.image')}
                            fullWidth
                            margin="normal"
                            {...control.register('image')}
                            variant="standard"
                        />
                        <TextField
                            label={t('Register-restaurant.address')}
                            fullWidth
                            margin="normal"
                            {...control.register('address')}
                            variant="standard"
                        />
                        <TextField
                            label={t('Register-restaurant.email')}
                            fullWidth
                            margin="normal"
                            {...control.register('email')}
                            variant="standard"
                        />
                        <TextField
                            label={t('Register-restaurant.detail')}
                            fullWidth
                            margin="normal"
                            {...control.register('detail')}
                            variant="standard"
                        />
                        <FormControl sx={{ minWidth: '100%' }}>
                            <InputLabel id="categoryLabel">{t('Register-restaurant.categories')}</InputLabel>
                            <Select
                                labelId="categoryLabel"
                                id="categorySelect"
                                multiple
                                autoWidth
                                fullWidth={true}
                                variant="standard"
                                value={selectedCategories}
                                {...control.register('categories')}
                                onChange={handleCategoryChange}
                                renderValue={(selected) => {
                                  return selected.map(category => Category.values.find(otherC => otherC.name === category)?.description).join(', ')
                                }}
                                className='overflow_ellipsis width_100'
                            >
                                {
                                    Category.values.map((category) => {
                                      return (
                                            <MenuItem key={category.name} value={category.name}>
                                                <Checkbox checked={selectedCategories.includes(category.name)}/>
                                                <ListItemText primary={t(category.description)}/>
                                            </MenuItem>
                                      )
                                    })
                                }
                            </Select>
                        </FormControl>
                        <FormControl sx={{ minWidth: '100%' }}>
                            <InputLabel id="shiftLabel">{t('Register-restaurant.shifts')}</InputLabel>
                            <Select
                                labelId="shiftLabel"
                                id="shiftSelect"
                                multiple
                                autoWidth
                                fullWidth={true}
                                variant="standard"
                                value={selectedShifts}
                                {...control.register('shifts')}
                                onChange={handleShiftChange}
                                renderValue={(selected) => {
                                  return selected.map(shift => Shift.values.find(otherS => otherS.name === shift)?.description).join(', ')
                                }}
                                className='overflow_ellipsis width_100'
                            >
                                {
                                    Shift.values.map((shift) => {
                                      return (
                                            <MenuItem key={shift.name} value={shift.name}>
                                                <Checkbox checked={selectedCategories.includes(shift.name)}/>
                                                <ListItemText primary={t(shift.description)}/>
                                            </MenuItem>
                                      )
                                    })
                                }
                            </Select>
                        </FormControl>
                        <Button type="submit" variant="contained" color="primary">
                            {t('register')}
                        </Button>
                    </FormControl>
                </RegisterRestaurantForm>
            </RegisterRestaurantWhiteBoxContainer>
        </MyContainer>
  )
}
