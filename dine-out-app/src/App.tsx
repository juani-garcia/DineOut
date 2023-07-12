import React from 'react'
import { ThemeProvider as StyledThemeProvider } from 'styled-components'
import { muiTheme, styledTheme } from '@/theme'
import Home from '@/components/MainLayout'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import MainContent from '@/components/Pages/MainContent'
import Restaurants from '@/components/Pages/Restaurants'
import { AuthProvider } from '@/hooks/auth/useAuth'
import Login from '@/components/Pages/Login'
import Register from '@/components/Pages/Register'
import { ThemeProvider as MuiThemeProvider } from '@mui/material/styles'
import Restaurant from '@/components/Pages/Restaurant'

function App (): JSX.Element {
  return (
        <StyledThemeProvider theme={styledTheme}>
            <MuiThemeProvider theme={muiTheme}>
                <AuthProvider>
                    <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
                        <Routes>
                            <Route
                                path='/'
                                element={
                                    <Home/>
                                }
                            >
                                <Route index element={<MainContent/>}/>
                                <Route path="restaurants"
                                       element={<Restaurants/>}/>
                                <Route path="restaurant/:id"
                                       element={<Restaurant/>}/>
                                <Route path="register"
                                       element={<Register/>}/>
                                <Route path="login"
                                       element={<Login/>}/>
                                <Route
                                    path="*"
                                    element={
                                        <>Vamo que vamo</>
                                    }
                                />
                            </Route>
                        </Routes>
                    </BrowserRouter>
                </AuthProvider>
            </MuiThemeProvider>
        </StyledThemeProvider>
  )
}

export default App
