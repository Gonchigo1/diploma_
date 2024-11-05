import {useEffect} from 'react'
import {useRouter} from 'next/router'
import {observer} from 'mobx-react-lite'

const Base = observer(() => {
  const router = useRouter()

  useEffect(() => {
    router.push('/dashboard')
  })

  return null
})

export default Base
