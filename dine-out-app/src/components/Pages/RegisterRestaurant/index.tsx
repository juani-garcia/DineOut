import React from 'react'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import WithMap from '@/components/Pages/RegisterRestaurant/withmap'

export default function RegisterRestaurant (): JSX.Element {
  return (
        <CustomGMapScriptLoad>
            <WithMap/>
        </CustomGMapScriptLoad>
  )
}
