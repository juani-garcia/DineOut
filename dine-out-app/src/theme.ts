import '@fontsource/montserrat/300.css'
import '@fontsource/montserrat/400.css'
import '@fontsource/montserrat/500.css'
import '@fontsource/montserrat/700.css'
import '@fontsource/montserrat/800.css'
import '@fontsource/montserrat/900.css'
import { createTheme } from '@mui/material'

export const styledTheme = {
  groovy: ['rgba(213, 51, 105, 1)', 'rgba(218, 174, 81, 1)'],
  backgroundTop: '#d53369',
  backgroundBottom: '#daae51',
  defaultDark: '#242B2A',
  defaultWhite: '#ffffff',
  boldWeight: '700',
  lightWeight: '300',
  black: '#000000'
}

export const muiTheme = createTheme({
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          backgroundColor: '#FFFFFF',
          boxShadow: '0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2)',
          transition: 'all 0.2s ease-in-out',
          '&:hover': {
            backgroundColor: '#FFFFFF',
            transform: 'scale(1.1)'
          }
        }
      }
    },
    MuiFormControl: {
      styleOverrides: {
        root: {
          justifyContent: 'center',
          alignItems: 'center'
        }
      }
    }
  },
  typography: {
    fontFamily: 'Montserrat, Roboto, Oxygen, sans-serif' // Replace with your HTML font and fallback options
  },
  palette: {
    primary: {
      main: '#9e9e9e'
    },
    secondary: {
      main: '#d53369'
    },
    action: {
      active: '#FFFFFF',
      hover: '#d53369'
    },
    info: {
      main: '#FFFFFF'
    }
  }
})
