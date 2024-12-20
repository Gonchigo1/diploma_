import {useEffect} from 'react'
import {Form, Input, InputNumber, Modal, Switch} from 'antd'
import {observer} from 'mobx-react-lite'

import {useStore} from '../../../context/mobxStore'

const FormItem = Form.Item

const BookTypeUpdate = observer(({
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
        createUpdate(fieldsValue)
      })
      .catch(errorInfo => {
        console.error('Maroon ! --> ~ file: createUpdateModal.js ~ line 38 ~ submitHandle ~ errorInfo', errorInfo)
      })
  }

  const createUpdate = (payload) => {
    const newPayload = Object.assign({...editData, ...payload})
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

  const validateCode = (_rule, control, callback) => {
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

  const {_icon, ...initialValues} = editData
  return (
    <Modal
      title={`Сургалтын төрөл ${isCreate ? 'бүртгэх' : 'засварлах'}`}
      onOk={() => submitHandle()}
      onCancel={backHandle}
      okText='Хадгалах'
      cancelText='Болих'
      confirmLoading={loading}
      open={modalOpen}
      forceRender
      maskClosable={false}
    >
      <br/>
      <Form {...formLayout}
        form={form}
        initialValues={initialValues}
        layout='vertical'
      >
        <FormItem
          label='Сургалтын нэр'
          name='name'
          rules={[{required: true, message: 'Сургалтын нэр бичнэ үү'}]}
          // className="mb10"
        >
          <Input placeholder='Сургалтын нэр'/>
        </FormItem>
        <FormItem
          label='Сургалтын код'
          name='code'
          rules={[
            {required: true, message: 'Код бичнэ үү'},
            {validator: (rule, control, callback) => validateCode(rule, control, callback)}
          ]}
          extra='Зөвхөн латин жижиг үсэг оруулна'
          // className="mb10"
        >
          <Input disabled={!isCreate} placeholder='Сургалтын код'/>
        </FormItem>
        <FormItem
          label='Эрэмбэ'
          name='order'
          rules={[{required: true, message: 'Эрэмбэ оруулна уу!'}]}
          // className="mb10"
        >
          <InputNumber formatter={value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')} min={1} step={1} placeholder='Дугаар'/>
        </FormItem>
        <FormItem
          label='Идэвхтэй эсэх'
          name='active'
          valuePropName='checked'
          className='mb10'
        >
          <Switch checkedChildren='Тийм' unCheckedChildren='Үгүй'/>
        </FormItem>
      </Form>
    </Modal>
  )
})

export default BookTypeUpdate