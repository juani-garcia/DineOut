import React from 'react'
import { BodyContainer, MainContainer } from './styles'
import Navbar from '../Elements/Navbar'
import Footer from '../Elements/Footer'
import { Outlet } from 'react-router-dom'

function Main (): JSX.Element {
  return (
        <MainContainer>
            <Navbar/>
            <BodyContainer>
                <Outlet/>
            </BodyContainer>
            <Footer/>
        </MainContainer>
  )
}

export default Main
