import React, { type ReactNode } from 'react'
import { useLoadScript } from '@react-google-maps/api'
import { type Library } from '@googlemaps/js-api-loader'
import { CircularProgress } from '@mui/material'

const gmapsApiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY ?? ''
const libraries = (process.env.REACT_APP_GOOGLE_MAPS_LIBRARIES?.split(',') ?? []) as Library[]

// eslint-disable-next-line react/prop-types
function CustomGMapScriptLoad ({ children }: { children: ReactNode }): JSX.Element {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: gmapsApiKey,
    libraries
  })

  if (loadError != null) {
    return <div>Error loading Google Maps</div> // TODO: Make this err msg pretty
  }

  return (
        <>
            {
                isLoaded
                  ? (
                      children
                    )
                  : (
                        <CircularProgress color="secondary" size="100px"/>
                    )
            }
        </>
  )
}

export default CustomGMapScriptLoad
