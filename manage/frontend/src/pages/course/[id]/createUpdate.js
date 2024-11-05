import {useEffect} from 'react'
import {Form, Input, Modal} from 'antd'
import {observer} from 'mobx-react-lite'
import {useStore} from '../../../context/mobxStore'

const FormItem = Form.Item

const BookUpdate = observer(
  ({modalOpen, handleUpdate, handleCreate, editData, onClose}) => {
    const [form] = Form.useForm()
    const referenceTypeStore = useStore('referenceTypeStore')
    const oxfordthinkersStore = useStore('oxfordThinkersStore')
    const loading = referenceTypeStore?.loading
    const isCreate = !editData?.id

    useEffect(() => {
      if (modalOpen) {
        form.resetFields()
        form.setFieldsValue(editData)
      }
    }, [editData, modalOpen])

    const submitHandle = () => {
      form
        .validateFields()
        .then((fieldsValue) => {
          createUpdate(fieldsValue)
        })
        .catch((errorInfo) => {
          console.error(
            'error',
            errorInfo
          )
        })
    }

    const handleRegisterOxford = (values) => {
      const response = oxfordthinkersStore.create(session?.token, values)
      if (response.result) {
        router.push('/course')
      } else {
        console.error('Failed to register:', response)
      }
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

    const formLayout = {
      labelCol: {
        xs: {span: 24},
        sm: {span: 24},
        md: {span: 24},
        lg: {span: 24},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 24},
        md: {span: 24},
        lg: {span: 24},
      },
    }
    const onFinish = (values) => {
      const formData = {
        ...values,
        bookId: '',
      }
      handleRegisterOxford(formData)
    }

    const {...initialValues} = editData || {}
    return (
      <Modal
        title={` ${isCreate ? 'Сэдэв бүртгэх' : 'Сэдэв шинэчлэх'}`}
        // width={900}
        onOk={() => submitHandle()}
        onCancel={backHandle}
        okText='Хадгалах'
        cancelText='Болих'
        confirmLoading={loading}
        open={modalOpen}
        forceRender
        maskClosable={false}
      >
        <Form
          {...formLayout}
          form={form}
          initialValues={initialValues}
          onFinish={onFinish}
          layout={'vertical'}
        >
          <FormItem
            label='Сэдвийн нэр'
            name='name'
            rules={[
              {required: true, message: 'Please input the topic name!'},
            ]}
          >
            <Input placeholder='Сэдвийн нэр' />
          </FormItem>
        </Form>
      </Modal>
    )
  }
)

export default BookUpdate
