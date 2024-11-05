import {Modal, Form, Input} from 'antd'
const CreateLesson = ({modalOpen, onClose, handleCreate}) => {
  const [form] = Form.useForm()

  const onFinish = (values) => {
    handleCreate(values, form)
  }

  return (
    <Modal
      visible={modalOpen}
      title='Сэдэв бүртгэх'
      onCancel={onClose}
      okText={'Бүртгэх'}
      cancelText='Болих'
      onOk={form.submit}
      maskClosable={false}
    >
      <Form form={form} layout='vertical' onFinish={onFinish}>
        <Form.Item
          name='lesson'
          label='Сэдэв'
          rules={[{required: true, message: 'Сэдэв оруулна уу!'}]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  )
}
export default CreateLesson 
