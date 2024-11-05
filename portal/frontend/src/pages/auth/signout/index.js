import {useEffect} from 'react'
import {signOut} from 'next-auth/react'
import {useRouter} from 'next/router'
import Loader from '../../../components/elements/loader'

import {useStore} from '@context/mobxStore'

const SignOut = () => {
    const authStore = useStore('authStore')
    const router = useRouter()
    const {query} = router

    useEffect(() => {
        signOut({redirect: false, callbackUrl: '/auth/login'})
            .then(signOutResponse => {
                router.push(signOutResponse.url)
            })
            .catch(e => {
                console.log(e)
                router.push( `/auth/signin`)
            })
    }, [router])
    return <Loader/>
}

export default SignOut