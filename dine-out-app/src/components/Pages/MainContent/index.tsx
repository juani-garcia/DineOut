import React from 'react'
import { Link } from 'react-router-dom'

function MainContent (): JSX.Element {
  return (
        <Link to="/restaurants">
            <h1>Hola como te va, boca?</h1>
        </Link>
  )
}

export default MainContent
