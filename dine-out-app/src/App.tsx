import React from 'react'
import { ThemeProvider } from 'styled-components'
import theme from './theme'
import Home from './components/MainLayout'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import MainContent from './components/Pages/MainContent'
import Restaurants from './components/Pages/Restaurants'

function App (): JSX.Element {
  return (
        <ThemeProvider theme={theme}>
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
                               element={<Restaurants/>}/> {/* TODO: Check if routes file is neccesary */}
                    </Route>
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
  )
}

export default App
