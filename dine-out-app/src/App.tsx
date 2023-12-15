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
import RegisterRestaurant from '@/components/Pages/RegisterRestaurant'
import MenuSection from '@/components/Pages/MenuSection'
import MenuItem from '@/components/Pages/MenuItem'
import CreateReservation from '@/components/Pages/CreateReservation'
import CreateReview from '@/components/Pages/CreateReview'
import Error from '@/components/Pages/Error'
import Reservations from '@/components/Pages/Reservations'
import EditRestaurant from '@/components/Pages/EditRestaurant'
import PasswordRecovery from '@/components/Pages/PasswordRecovery'

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
                                <Route
                                    path='/restaurant'
                                >
                                    <Route index element={ <Restaurants/> }/>
                                    <Route path=":id">
                                        <Route path="view" element={<Restaurant/>}/>
                                        <Route path="review" element={<CreateReview/>}/>
                                        <Route
                                            path=""
                                            element={
                                                <Error/>
                                            }
                                        />
                                    </Route>
                                    <Route path="register"
                                           element={<RegisterRestaurant/>}/>
                                    <Route path="edit"
                                           element={<EditRestaurant/>}/>
                                    <Route path="section"
                                           element={<MenuSection/>}/>
                                    <Route path="section/:id/edit"
                                           element={<MenuSection/>}/>
                                    <Route path="item"
                                           element={<MenuItem/>}/>
                                    <Route
                                        path=""
                                        element={
                                            <Error/>
                                        }
                                    />
                                </Route>
                                <Route path="reserve/:id"
                                       element={<CreateReservation/>}/>
                                <Route path="reservation"
                                       element={<Reservations/>}/>
                                <Route path="register"
                                       element={<Register/>}/>
                                <Route path="login"
                                       element={<Login/>}/>
                                <Route path="password-recovery"
                                       element={<PasswordRecovery/>}/>
                                <Route
                                    path="error"
                                    element={
                                        <Error/>
                                    }
                                />
                                <Route
                                    path="*"
                                    element={
                                        <Error/>
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
