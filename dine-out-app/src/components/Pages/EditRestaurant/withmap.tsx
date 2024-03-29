import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { RegisterRestaurantForm, RegisterRestaurantWhiteBoxContainer } from './styles'
import {
  Button,
  Checkbox,
  CircularProgress,
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
import { useUpdateRestaurant } from '@/hooks/Restaurants/useUpdateRestaurant'
import { Zone } from '@/types/enums/Zone'
import { useAuth } from '@/hooks/auth/useAuth'
import { useNavigate } from 'react-router-dom'
import { useSnackbar } from 'notistack'
import { localPaths, roles } from '@/common/const'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import { Autocomplete, GoogleMap, MarkerF } from '@react-google-maps/api'
import { MapSearchContainer } from '@/components/Elements/MapSearch/styles'
import type Restaurant from '@/types/models/Restaurant'
import Error from '@/components/Pages/Error'
import useRestaurant from '@/hooks/Restaurants/useRestaurant'
import { HttpStatusCode } from 'axios'

// interface RegisterRestaurantFormInput {
//     name: string,
//     image?: string,
//     address: string,
//     email: string,
//     detail: string,
//     categories: Category[],
//     shifts: Shift[]
// }

interface LocationType {
  lat: number
  lng: number
  address: string | undefined
  zone: Zone | undefined
}

export default function WithMap (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control, setValue } = useForm()
  const [selectedCategories, setselectedCategories] = useState<string[]>([])
  const [selectedShifts, setSelectedShifts] = useState<string[]>([])
  const { isLoading, updateRestaurant } = useUpdateRestaurant()
  const { user } = useAuth()
  const { enqueueSnackbar } = useSnackbar()
  const navigate = useNavigate()
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const [error, setError] = useState<number | null>(null)
  const { isLoading: isLoadingRestaurant, restaurant: requestRestaurant } = useRestaurant()

  const [selectedLocation, setSelectedLocation] = useState<LocationType>({
    lat: 0,
    lng: 0,
    address: undefined,
    zone: undefined
  })
  const autocompleteRef = useRef<google.maps.places.Autocomplete | null>(null)
  const mapRef = useRef<google.maps.Map | null>(null)

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: localPaths.RESTAURANTS + '/register' }
      })
    } else if (!user.roles.includes(roles.RESTAURANT)) {
      navigate('/')
    } else if (user.restaurantId === undefined || user.restaurantId === null) {
      navigate('/restaurant/register')
    }

    requestRestaurant(Number(user?.restaurantId)).then(response => {
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
  }, [user])

  useEffect(() => {
    if (restaurant === undefined) return
    setValue('name', restaurant?.name ?? '')
    setValue('email', restaurant?.mail ?? '')
    setValue('detail', restaurant?.detail ?? '')
    setValue('shifts', restaurant.shifts)
    setValue('categories', restaurant.categories)
    setselectedCategories(restaurant.categories)
    setSelectedShifts(restaurant.shifts)
    setSelectedLocation({
      lat: parseFloat(restaurant.lat),
      lng: parseFloat(restaurant.lat),
      address: restaurant.address,
      zone: Zone.fromName(restaurant.zone)
    })
    mapRef.current?.setCenter(new google.maps.LatLng({
      lat: parseFloat(restaurant.lat),
      lng: parseFloat(restaurant.lat)
    }))
    mapRef.current?.setZoom(16)
  }, [restaurant, mapRef])

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
    if (selectedLocation.address === '' || selectedLocation.address === undefined) {
      enqueueSnackbar(t('Errors.tryAgain'), {
        variant: 'warning'
      })
      return
    }
    if (user?.restaurantId === undefined || user.restaurantId === null) {
      setRestaurant(undefined)
      setError(HttpStatusCode.Unauthorized)
      return
    }
    updateRestaurant(user?.restaurantId, data.name, selectedLocation.address, data.email, data.detail, selectedLocation.zone?.name ?? '', selectedLocation.lat, selectedLocation.lng, data.categories, data.shifts, restaurant?.menuSectionsOrder ?? []).then(
      (response) => {
        if (response.status === 200 || response.status === 201) {
          navigate('/restaurant/' + user.restaurantId.toString() + '/view')
        } else {
          enqueueSnackbar(t('Errors.tryAgain'), {
            variant: 'warning'
          })
        }
      }
    ).catch(
      (e: any) => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      }
    )
  }

  const center = new google.maps.LatLng({
    lat: -34.587608, // Set the initial center of the map here
    lng: -58.437748
  })

  const containerStyle = {
    width: '80%',
    height: '350px',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    borderRadius: '24px'
  }

  const onPlaceChanged = (): void => {
    if (autocompleteRef.current == null) return
    const place = autocompleteRef.current.getPlace()
    if (place.geometry != null) {
      const { lat, lng } = place.geometry.location ?? new google.maps.LatLng({ lat: 0, lng: 0 })

      setSelectedLocation({
        lat: lat(),
        lng: lng(),
        address: place.formatted_address,
        zone: ((place.vicinity != null) ? Zone.fromDescription(place.vicinity) : undefined)
      })
      mapRef.current?.setCenter(new google.maps.LatLng({
        lat: lat(),
        lng: lng()
      }))
      mapRef.current?.setZoom(16)
    } else {
      setSelectedLocation({ lat: 0, lng: 0, address: undefined, zone: undefined })
    }
  }

  if (error !== null) return <Error errorProp={error}/>

  return (
        <MyContainer>
            <Title>{t('Edit-restaurant.header')}</Title>
            <RegisterRestaurantWhiteBoxContainer>
                {
                    (isLoadingRestaurant)
                      ? (
                            <CircularProgress color="secondary" size="100px"/>
                        )
                      : (
                            <>
                                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                                <RegisterRestaurantForm onSubmit={handleSubmit(onSubmit)}>
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
                                        />
                                        <TextField
                                            label={t('Register-restaurant.email')}
                                            fullWidth
                                            margin="normal"
                                            {...control.register('email')}
                                        />
                                        <CustomGMapScriptLoad>
                                            <MapSearchContainer>
                                                <Autocomplete
                                                    onLoad={(auto) => (autocompleteRef.current = auto)}
                                                    onPlaceChanged={onPlaceChanged}
                                                >
                                                    <input style={{
                                                      boxSizing: 'border-box',
                                                      border: '1px solid #ccc',
                                                      borderRadius: '4px',
                                                      fontSize: '16px',
                                                      height: '40px',
                                                      width: '500px',
                                                      padding: '10px',
                                                      marginBottom: '21px'
                                                    }} type="text" placeholder={t('MapSearch.location') ?? ''}/>
                                                </Autocomplete>
                                                <GoogleMap
                                                    mapContainerStyle={containerStyle}
                                                    onLoad={(map) => {
                                                      mapRef.current = map
                                                      map.setCenter(center)
                                                      map.setZoom(11)
                                                    }}
                                                >
                                                    <MarkerF
                                                        position={{
                                                          lat: selectedLocation.lat,
                                                          lng: selectedLocation.lng
                                                        }}
                                                        animation={window.google.maps.Animation.DROP}
                                                    />
                                                </GoogleMap>
                                            </MapSearchContainer>
                                        </CustomGMapScriptLoad>

                                        <TextField
                                            label={t('Register-restaurant.detail')}
                                            fullWidth
                                            margin="normal"
                                            {...control.register('detail')}
                                        />
                                        <FormControl sx={{ minWidth: '100%', marginTop: '20px' }}>
                                            <InputLabel
                                                id="categoryLabel">{t('Register-restaurant.categories')}</InputLabel>
                                            <Select
                                                labelId="categoryLabel"
                                                id="categorySelect"
                                                multiple
                                                fullWidth={true}
                                                label={t('Register-restaurant.categories')}
                                                value={selectedCategories}
                                                {...control.register('categories')}
                                                onChange={handleCategoryChange}
                                                renderValue={(selected) => {
                                                  return selected.map(category => {
                                                    const categoryNameToBeTranslated = Category.fromName(category)?.description
                                                    if (categoryNameToBeTranslated === undefined) {
                                                      return 'undefined'
                                                    }
                                                    return t(categoryNameToBeTranslated)
                                                  }).join(', ')
                                                }}
                                                className='overflow_ellipsis width_100'
                                            >
                                                {
                                                    Category.values.map((category) => {
                                                      return (
                                                            <MenuItem key={category.name} value={category.name}>
                                                                <Checkbox
                                                                    checked={selectedCategories.includes(category.name)}/>
                                                                <ListItemText primary={t(category.description)}/>
                                                            </MenuItem>
                                                      )
                                                    })
                                                }
                                            </Select>
                                        </FormControl>
                                        <FormControl sx={{ minWidth: '100%', marginTop: '20px' }}>
                                            <InputLabel id="shiftLabel">{t('Register-restaurant.shifts')}</InputLabel>
                                            <Select
                                                labelId="shiftLabel"
                                                id="shiftSelect"
                                                multiple
                                                label={t('Register-restaurant.shifts')}
                                                fullWidth={true}
                                                value={selectedShifts}
                                                {...control.register('shifts')}
                                                onChange={handleShiftChange}
                                                renderValue={(selected) => {
                                                  return selected.map(shift => {
                                                    const shiftNameToBeTranslated = Shift.fromName(shift)?.description
                                                    if (shiftNameToBeTranslated === undefined) {
                                                      return 'undefined'
                                                    }
                                                    return t(shiftNameToBeTranslated)
                                                  }).join(', ')
                                                }}
                                                className='overflow_ellipsis width_100'
                                            >
                                                {
                                                    Shift.values.map((shift) => {
                                                      return (
                                                            <MenuItem key={shift.name} value={shift.name}>
                                                                <Checkbox checked={selectedShifts.includes(shift.name)}/>
                                                                <ListItemText primary={t(shift.description)}/>
                                                            </MenuItem>
                                                      )
                                                    })
                                                }
                                            </Select>
                                        </FormControl>
                                        {
                                            isLoading
                                              ? (
                                                    <CircularProgress color="secondary" size="50px"/>
                                                )
                                              : (
                                                    <Button type="submit" variant="contained" color="primary"
                                                            sx={{ marginTop: '20px' }}>
                                                        {t('edit')}
                                                    </Button>
                                                )
                                        }
                                    </FormControl>
                                </RegisterRestaurantForm>
                            </>
                        )
                }
            </RegisterRestaurantWhiteBoxContainer>
        </MyContainer>
  )
}
