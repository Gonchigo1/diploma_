import {useEffect} from 'react'
import {Form, Input, InputNumber, Modal, Switch} from 'antd'
import {observer} from 'mobx-react-lite'

import {useStore} from '../../../context/mobxStore'

const FormItem = Form.Item

const TeacherUpdate = observer(({
  modalOpen,
  handleUpdate,
  handleCreate,
  editData,
  onClose
}) => {

  const [form] = Form.useForm()
  const referenceTypeStore = useStore('referenceTypeStore')
  const loading = referenceTypeStore?.loading
  const isCreate = !editData?.id

  useEffect(() => {
    form.resetFields()
  }, [editData])

  const submitHandle = () => {
    form.validateFields()
      .then(fieldsValue => {
        const payLoad = {...fieldsValue, role: 'TEACHER'}
        createUpdate(payLoad)
      })
      .catch(errorInfo => {
        console.error('Maroon ! --> ~ file: createUpdateModal.js ~ line 38 ~ submitHandle ~ errorInfo', errorInfo)
      })
  }

  const createUpdate = (payload) => {
    const newPayload = Object.assign({...editData, ...payload, role: 'TEACHER'})
    if (newPayload?.id) {
      handleUpdate(newPayload, form)
    } else {
      handleCreate(newPayload, form)
    }
  }

  const backHandle = () => {
    form.resetFields()
    onClose()
  }

  const _validateCode = (_rule, control, callback) => {
    const regex = /^[a-z]+$/
    if (control) {
      const result = regex.test(control)
      if (result) {
        callback()
      } else
        callback('Хэлний код буруу байна!')
    } else
      callback()
  }

  const formLayout = {
    labelCol: {
      xs: {span: 24},
      sm: {span: 24},
      md: {span: 24},
      lg: {span: 24}
    },
    wrapperCol: {
      xs: {span: 24},
      sm: {span: 24},
      md: {span: 24},
      lg: {span: 24}
    }
  }

  const {...initialValues} = editData
  return (
    <Modal
      title={`Багш ${isCreate ? 'бүртгэх' : 'засварлах'}`}
      width={600}
      onOk={() => submitHandle()}
      onCancel={backHandle}
      okText='Хадгалах'
      cancelText='Болих'
      confirmLoading={loading}
      open={modalOpen}
      forceRender
    >
      <Form {...formLayout} form={form} initialValues={initialValues} layout={'vertical'}>
        <FormItem
          label='Сургуулийн Нэр'
          name='orgName' 
          rules={[{required: true, message: 'Нэр бичнэ үү'}]}
        >
          <Input placeholder='Сургуулийн Нэр' />
        </FormItem>
  
        <FormItem
          label='Багшийн овог'
          name='lastName' 
          rules={[{required: true, message: 'Овог бичнэ үү'}]}
        >
          <Input placeholder='Багшийн овог' />
        </FormItem>
  
        <FormItem
          label='Багшийн Нэр'
          name='firstName' 
          rules={[{required: true, message: 'Нэр бичнэ үү'}]}
        >
          <Input placeholder='Багшийн Нэр' />
        </FormItem>
  
        <FormItem
          label='Утас'
          name='mobile'
          rules={[{required: true, message: 'Гар утасны дугаараа бичнэ үү'}]}
        >
          <InputNumber
            style={{width: '100%'}}
            // formatter={value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 
            min={1} step={1} 
            placeholder='Гар утасны дугаар' />
        </FormItem>
  
        <FormItem
          label='И-мейл'
          name='email'
          onChange={(e) => form.setFieldValue('username', e.target?.value)}
          rules={[{required: true, message: 'И-мейл бичнэ үү'}]}
        >
          <Input disabled={!isCreate} placeholder='И-мейл' />
        </FormItem>
  
        <FormItem
          label='Нэвтрэх нэр'
          name='username' 
        //   rules={[{required: true, message: 'Нэвтрэх нэр бичнэ үү'}]}
        >
          <Input disabled={true} placeholder='Нэвтрэх нэр' />
        </FormItem>

        <FormItem
          label='Нууц үг'
          name='password'
        //   rules={[{required: true, message: 'Нууц үг бичнэ үү'}]}
        >
          <Input placeholder='Нууц үг' />
        </FormItem>
  
        <FormItem
          label='Идэвхтэй эсэх'
          name='active'
          valuePropName='checked'
        >
          <Switch checkedChildren='Тийм' unCheckedChildren='Үгүй' />
        </FormItem>
      </Form>
    </Modal>
  )
})

export default TeacherUpdate