import React from 'react'
import { useParams } from 'react-router-dom'

function Restaurant (): JSX.Element {
  const params = useParams()
  return (<>{params.id}</>) // TODO: Check errors, 404, 403, etc.
}

export default Restaurant
