import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { FormControl, IconButton, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { SearchBoxContainer, SearchBoxForm } from '@/components/Elements/SearchBox/styles'
import { useTranslation } from 'react-i18next'
import SearchIcon from '@mui/icons-material/Search'
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom'
import { Shift } from '@/types/enums/Shift'
import Category from '@/types/enums/Category'
import { Zone } from '@/types/enums/Zone'

function SearchBox (): JSX.Element {
  const { register, handleSubmit, setValue } = useForm()
  const { t } = useTranslation()
  const [queryParams, setQueryParams] = useSearchParams()
  const navigate = useNavigate()
  const location = useLocation()

  const onSubmit = (data: any): void => {
    const existingParams = new URLSearchParams(queryParams.toString())

    setQueryParams({ ...data, page: existingParams.get('page') ?? '1' })

    if (location.pathname === '/') {
      const existingParams = new URLSearchParams(data)
      existingParams.set('page', '1')
      navigate('/restaurant?' + existingParams.toString())
    }
  }

  useEffect(() => {
    setValue('match', queryParams.get('match') ?? '')
    setValue('category', queryParams.get('category') ?? '')
    setValue('zone', queryParams.get('zone') ?? '')
    setValue('shift', queryParams.get('shift') ?? '')
  }, [queryParams, setValue])

  const whenOptions = Shift.values
  const categoryOptions = Category.values
  const zoneOptions = Zone.values

  return (
        <SearchBoxContainer>
            {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
            <SearchBoxForm onSubmit={handleSubmit(onSubmit)}>
                <TextField label={t('SearchBox.search')} {...register('match')} />
                <FormControl sx={{ minWidth: 101 }}>
                    <InputLabel id="categoryLabel">{t('SearchBox.category')}</InputLabel>
                    <Select {...register('category')}
                            labelId="categoryLabel"
                            defaultValue={queryParams.get('category') ?? ''}
                            id="categorySelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.category')}
                    >
                        <MenuItem value="">
                            <em>{t('SearchBox.none')}</em>
                        </MenuItem>
                        {
                            categoryOptions.map(category => (
                              (category != null) &&
                                <MenuItem key={category.name} value={category.name}>
                                    {t(category.description)}
                                </MenuItem>
                            ))
                        }
                    </Select>
                </FormControl>
                <FormControl sx={{ minWidth: 100 }}>
                    <InputLabel id="whereLabel">{t('SearchBox.where')}</InputLabel>
                    <Select {...register('zone')}
                            labelId="whereLabel"
                            defaultValue={queryParams.get('zone') ?? ''}
                            id="whereSelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.where')}
                    >
                        <MenuItem value="">
                            <em>{t('SearchBox.none')}</em>
                        </MenuItem>
                        {
                            zoneOptions.map(zone => (
                              (zone != null) &&
                                <MenuItem key={zone.name} value={zone.name}>
                                    {zone.description}
                                </MenuItem>
                            ))
                        }
                    </Select>
                </FormControl>
                <FormControl sx={{ minWidth: 100 }}>
                    <InputLabel id="whenLabel">{t('SearchBox.when')}</InputLabel>
                    <Select {...register('shift')}
                            labelId="whenLabel"
                            defaultValue={queryParams.get('shift') ?? ''}
                            id="whenSelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.when')}
                    >
                        <MenuItem value="">
                            <em>{t('SearchBox.none')}</em>
                        </MenuItem>
                        {
                            whenOptions.map(shift => (
                              (shift != null) &&
                                <MenuItem key={shift.name} value={shift.name}>
                                    {t(shift.description)}
                                </MenuItem>
                            ))
                        }
                    </Select>
                </FormControl>
                <IconButton type="submit" color="primary" aria-label="search">
                    <SearchIcon/>
                </IconButton>
            </SearchBoxForm>
        </SearchBoxContainer>
  )
}

export default SearchBox
