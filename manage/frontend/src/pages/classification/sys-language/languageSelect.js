import {useEffect} from 'react'
import {observer} from 'mobx-react-lite'
import {Select} from 'antd'
import {useStore} from '../../../context/mobxStore'

const LanguageSelect = observer(({
  placeHolder,
  token,
  defaultValue,
  onChange,
  allowClear,
  value,
}) => {

  const sysLanguageStore = useStore('sysLanguageStore')

  useEffect(() => {
    if (sysLanguageStore?.selectData?.length === 0 && !sysLanguageStore?.selectLoading)
      sysLanguageStore.fetchSelect({active: true}, token)
  }, [])

  return (
    <Select
      onChange={onChange}
      value={value}
      style={{width: '100%'}}
      defaultValue={defaultValue === undefined ? 'mn' : defaultValue}
      allowClear={allowClear ? allowClear : false}
      placeholder={placeHolder || 'Сонгох'}
    >
      {
        sysLanguageStore.selectData?.map((item) => (
          <Select.Option key={item?.code} value={item?.code}>{item?.name}</Select.Option>
        ))
      }
    </Select>
  )
})

export default LanguageSelect