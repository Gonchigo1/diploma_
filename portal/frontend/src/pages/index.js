import {observer} from "mobx-react-lite"
import {useRouter} from "next/router"
import {useEffect} from "react"

// import {Loader} from "@element"
import {Spin} from "antd";

const Base = observer(() => {
    const router = useRouter()

    useEffect(() => {
        router.push('/home')
    })

    return <Spin size='small' />
})

export default Base