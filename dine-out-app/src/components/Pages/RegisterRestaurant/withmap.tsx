import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect, useRef, useState } from 'react'
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
import { useCreateRestaurant } from '@/hooks/Restaurants/useCreateRestaurant'
import { Zone } from '@/types/enums/Zone'
import { useAuth } from '@/hooks/auth/useAuth'
import { redirect, useNavigate } from 'react-router-dom'
import { useSnackbar } from 'notistack'
import { roles } from '@/common/const'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import { Autocomplete, GoogleMap, MarkerF } from '@react-google-maps/api'
import { MapSearchContainer } from '@/components/Elements/MapSearch/styles'

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
  const { handleSubmit, control } = useForm()
  const [selectedCategories, setselectedCategories] = useState<string[]>([])
  const [selectedShifts, setSelectedShifts] = useState<string[]>([])
  const { isLoading, createRestaurant } = useCreateRestaurant()
  const { user, setUserRestaurantId } = useAuth()
  const { enqueueSnackbar } = useSnackbar()
  const navigate = useNavigate()
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
        state: { from: '/restaurant/register' }
      })
    } else if (!user.roles.includes(roles.RESTAURANT)) {
      navigate('/')
    } else if (user.restaurantId !== undefined && user.restaurantId !== null) {
      navigate('/restaurant/' + user.restaurantId.toString() + '/view')
    }
  }, [user])

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
    console.log(selectedLocation)
    createRestaurant(data.name, selectedLocation.address, data.email, data.detail, selectedLocation.zone?.name ?? '', selectedLocation.lat, selectedLocation.lng, data.categories, data.shifts).then(
      (response) => {
        if (response.status === 200 || response.status === 201) {
          const locationHeader = response.headers.location.split('/')
          const resId = parseInt(locationHeader[locationHeader.length - 1])
          if (resId === null || resId === undefined) return
          setUserRestaurantId(resId)
          console.log(isLoading)
          redirect('/restaurant/' + resId.toString() + '/view')
        } else {
          enqueueSnackbar(t('Errors.tryAgain'), {
            variant: 'warning'
          })
        }
      }
    ).catch(
      (e) => {
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

      console.log(place.vicinity)
      console.log(place)
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
                            <InputLabel id="categoryLabel">{t('Register-restaurant.categories')}</InputLabel>
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
                                  return selected.map(shift => Shift.values.find(otherS => otherS.name === shift)?.description).join(', ')
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
                        <Button type="submit" variant="contained" color="primary" sx={{ marginTop: '20px' }}>
                            {t('register')}
                        </Button>
                    </FormControl>
                </RegisterRestaurantForm>
            </RegisterRestaurantWhiteBoxContainer>
        </MyContainer>
  )
}
