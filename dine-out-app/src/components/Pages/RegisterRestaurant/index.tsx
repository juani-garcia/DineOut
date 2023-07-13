import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { RegisterRestaurantWhiteBoxContainer, RegisterRestaurantForm, Header } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField, InputLabel, Select, MenuItem, ListItemText, SelectChangeEvent } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useRegister } from '@/hooks/auth/useRegister'
import Category from '@/types/enums/Category'

export default function RegisterRestaurant (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const [selectedCategories, setselectedCategories] = useState<string[]>([]);
  const handleCategoryChange = (event: SelectChangeEvent<typeof selectedCategories>) => {
    const {
        target: { value },
    } = event;
    setselectedCategories(
        typeof value === 'string' ? value.split(',') : value,
    );
  };

  return (
    <MyContainer>
        <Title>{t('Register-restaurant')}</Title>
        <RegisterRestaurantWhiteBoxContainer>
            <RegisterRestaurantForm>
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
                            value={selectedCategories}
                            {...control.register('categories')}
                            onChange={handleCategoryChange}
                            renderValue={(selected) => (selected as string[]).join(', ')}
                            className='overflow_ellipsis width_100'
                        >
                            {
                                Category.values.map((category) => {
                                    return (
                                        <MenuItem key={category.name} value={category.name}>
                                            <Checkbox checked={selectedCategories.indexOf(category.name) > -1}/>
                                            <ListItemText primary={t(category.description)}/>
                                        </MenuItem>
                                    )
                                })
                            }
                        </Select>
                    </FormControl>
                    <TextField
                        label={t('Register-restaurant.categories')}
                        fullWidth
                        margin="normal"
                        {...control.register('categories')}
                        variant="standard"
                    />
                    <Button type="submit" variant="contained" color="primary">
                        {t('register')}
                    </Button>
                </FormControl>
            </RegisterRestaurantForm>
        </RegisterRestaurantWhiteBoxContainer>
    </MyContainer>
  )
}
