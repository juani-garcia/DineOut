import React from 'react'
import { ThemeProvider } from 'styled-components'
import theme from './theme'
import Home from './components/MainLayout'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import MainContent from './components/Pages/MainContent'
import Restaurants from './components/Pages/Restaurants'
import { AuthProvider } from './hooks/auth/useAuth'
import Login from './components/Pages/Login'

function App (): JSX.Element {
  return (
        <ThemeProvider theme={theme}>
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
        </ThemeProvider>
  )
}

export default App
