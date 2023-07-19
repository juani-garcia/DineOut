import React from 'react'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import WithMap from '@/components/Pages/EditRestaurant/withmap'

export default function EditRestaurant (): JSX.Element {
  return (
        <CustomGMapScriptLoad>
            <WithMap/>
        </CustomGMapScriptLoad>
  )
}
