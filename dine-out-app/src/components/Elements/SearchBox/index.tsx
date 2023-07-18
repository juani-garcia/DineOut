import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { FormControl, IconButton, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { SearchBoxContainer, SearchBoxForm } from '@/components/Elements/SearchBox/styles'
import { useTranslation } from 'react-i18next'
import SearchIcon from '@mui/icons-material/Search'
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom'

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
      navigate('/restaurants?' + existingParams.toString())
    }
  }

  useEffect(() => {
    setValue('match', queryParams.get('match') ?? '')
    setValue('category', queryParams.get('category') ?? '')
    setValue('zone', queryParams.get('zone') ?? '')
    setValue('shift', queryParams.get('shift') ?? '')
  }, [queryParams, setValue])

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
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="Jose Cruz">Jose Cruz</MenuItem>
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
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="4">4</MenuItem>
                        <MenuItem value="5">5</MenuItem>
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
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="6">6</MenuItem>
                        <MenuItem value="7">7</MenuItem>
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
