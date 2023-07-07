import React from 'react'
import { ThemeProvider as StyledThemeProvider } from 'styled-components'
import { muiTheme, styledTheme } from '@/theme'
import Home from '@/components/MainLayout'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import MainContent from '@/components/Pages/MainContent'
import Restaurants from '@/components/Pages/Restaurants'
import { AuthProvider } from '@/hooks/auth/useAuth'
import Login from '@/components/Pages/Login'
import { ThemeProvider as MuiThemeProvider } from '@mui/material/styles'

function App (): JSX.Element {
  return (
        <StyledThemeProvider theme={styledTheme}>
            <MuiThemeProvider theme={muiTheme}>
                <AuthProvider>
                    <BrowserRouter> { /* TODO: Check basename? */}
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
                                <Route path="login"
                                       element={<Login/>}/>
                            </Route>
                        </Routes>
                    </BrowserRouter>
                </AuthProvider>
            </MuiThemeProvider>
        </StyledThemeProvider>
  )
}

export default App
