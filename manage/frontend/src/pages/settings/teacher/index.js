import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import {Button, Card, Divider, Form, Input, message, Popconfirm, Table, Tag, Tooltip} from 'antd'
import {DeleteTwoTone, EditTwoTone, PlusOutlined} from '@ant-design/icons'
import dynamic from 'next/dynamic'
import numeral from 'numeral'

import {useStore} from '../../../context/mobxStore'
import {checkAuthRole} from '../../../common/util/auth'

const FormItem = Form.Item

const PageHeader = dynamic(() => import('../../../components/elements/pageHeader'))
const CreateUpdateModal = dynamic(() => import('./createUpdate'))

const Teacher = observer(() => {
  const [form] = Form.useForm()
  const {data: session = {}} = useSession()
  const userStore = useStore('userStore')
  const {data, loading, searchFormValues,_setSearchFormValues} = userStore
  const {list, pagination} = data
  const [modalOpen, setModalOpen] = useState(false)
  const [editData, setEditData] = useState({})

  useEffect(() => {
    if (session?.token) {
      refreshTable()
    }
  }, [session])

  const refreshTable = (params) => {
    const payload = {
      ...params,
      role: 'TEACHER'}
    userStore.fetchList(payload, session?.token)
  }

  const handleTableChange = (pagination) => {
    const params = {
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize
    }

    userStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const handleFormReset = () => {
    form.resetFields()
    userStore.setSearchFormValues({})
    refreshTable()
  }

  const handleSearch = () => {
    const searchValues = form.getFieldsValue()
    userStore.setSearchFormValues(searchValues)
    refreshTable(searchValues)
  }

  const showModal = (action, values) => {
    switch (action) {
      case 'CREATE':
        setEditData({})
        break
      case 'UPDATE':
        setEditData(values)
        break
      default:
        return ''
    }
    setModalOpen(true)
  }

  const handleCreate = (fields, form) => {
    userStore.create(fields, session?.token).then(response => {
      if (response.result === true && response.data) {
        message.success('Багш амжилттай бүртгэлээ')
        form.resetFields()
        setModalOpen(false)
        refreshTable()
      } else {
        message.error(`Багш бүртгэхэд алдаа гарлаа: ${response.message}`)
      }
    }).catch(e => {
      console.log(e)
      message.error(`Багш бүртгэхэд алдаа гарлаа: ${e.message}`)
    })
  }

  const handleUpdate = (fields, form) => {
    userStore.update(Object.assign({id: editData.id}, fields), session?.token)
      .then(response => {
        if (response.result === true && response.data) {
          message.success('Багш амжилттай засварлагдлаа')
          form.resetFields()
          setModalOpen(false)
          setEditData({})
          refreshTable()
        } else {
          message.error(`Багш засварлахад алдаа гарлаа: ${response.message}`)
        }
      })
      .catch(e => {
        console.log(e)
        message.error(`Багш засварлахад алдаа гарлаа: ${e.message}`)
      })
  }

  const handleDelete = id => {
    userStore.deleteOne(id ,session?.token)
      .then(response => {
        if (response.result === true && response.data) {
          message.success('Багш амжилттай устгалаа')
          handleFormReset()
        } else {
          message.error(`Багш устгахад алдаа гарлаа: ${response.message}`)
        }
      })
      .catch(e => {
        console.log(e)
        message.error(`Багш устгахад алдаа гарлаа: ${e.message}`)
      })
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      width: '50px',
      align: 'center',
      render: (_, __, index) => index + 1 + pagination.currentPage * pagination.pageSize,
    },
    {
      title: 'Сургуулийн Нэр',
      dataIndex: 'orgName',
      width: 200
    },
    {
      title: 'Багшийн овог',
      dataIndex: 'lastName',
      width: 200
    },
    {
      title: 'Багшийн Нэр',
      dataIndex: 'firstName',
      width: 200
    },
    {
      title: 'Утас',
      dataIndex: 'mobile',
      width: 180,
      align: 'center',
      render: text => <Tag color='blue'>
        {text}
      </Tag>
    },
    {
      title: 'И-мейл',
      dataIndex: 'email',
      width: 200,
    },
    {
      title: 'Идэвхтэй эсэх',
      dataIndex: 'active',
      align: 'center',
      width: 140,
      render: text => text ? <Tag color='green'>Тийм</Tag> : <Tag color='purple'>Үгүй</Tag>
    },
    {
      title: 'Үйлдэл',
      width: 220,
      align: 'center',
      render: (text, record) => (
        <>
          <Tooltip placement='top' title='Засах'>
            <Button
              icon={<EditTwoTone />}
              onClick={() => showModal('UPDATE', record)}
              style={{color: 'green'}}
              shape='circle'
            />
          </Tooltip>
          <Divider type='vertical' />
          <Tooltip placement='top' title='Устгах'>
            <Popconfirm
              title='Устгах уу ?'
              onConfirm={() => handleDelete(record)}
              okText='Тийм'
              cancelText='Үгүй'
            >
              <Button icon={<DeleteTwoTone twoToneColor={'red'}/>} shape='circle' />
            </Popconfirm>
          </Tooltip>
        </>
      )
    }
  ]
  const headerActions = (
    checkAuthRole('ROLE_USER_MANAGE', session?.applicationRoles) ? (
      <Button icon={<PlusOutlined />} type='primary' onClick={() => showModal('CREATE')}>
        Багш бүртгэх
      </Button>
    ) : '')

  const renderFilterForm = () => (
    <Form form={form} onFinish={handleSearch} layout='inline'>
      <FormItem
        label='Нэр'
        name='firstName'
        className='mb10'
      >
        <Input placeholder='Нэр' />
      </FormItem>
      <FormItem
        label='И-мейл'
        name='email'
        className='mb10'
      >
        <Input placeholder='И-мейл' />
      </FormItem>
      <FormItem>
        <Button type='primary' htmlType='submit'>
            Хайх
        </Button>
        <Button style={{marginLeft: 8}} onClick={handleFormReset}>
            Цэвэрлэх
        </Button>
      </FormItem>
    </Form>
  )
  return (
    <>
      <NextSeo title='Багш - UniCourse' />
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/'
          },
          {
            title: 'Тохиргоо',
            link: '/settings/teacher',
          }
        ]}
        title='Багш'
        action={headerActions}
      />
      <>
        <Card bordered={false}>
          {renderFilterForm()}
        </Card>
        <br />
        <Table
          rowKey='key'
          size='middle'
          loading={loading}
          columns={columns}
          dataSource={list || []}
          pagination={{
            ...pagination,
            showQuickJumper: true,
            showSizeChanger: true,
            pageSizeOptions: ['20', '50', '100'],
            defaultPageSize: 100,
            locale: {
              items_per_page: '/ хуудас',
              jump_to: 'Шилжих',
              jump_to_confirm: 'Confirm',
              page: 'Хуудас',
              prev_page: 'Өмнөх',
              next_page: 'Дараах',
              prev_5: 'Өмнөх 5',
              next_5: 'Дараах 5',
              prev_3: 'Өмнөх 3',
              next_3: 'Дараах 3',
            },
          }}
          onChange={handleTableChange}
          className='custom-table'
          title={() => (
            <div>
              Нийт багш: <b>{pagination?.total && numeral(pagination.total).format('0,0') || 0}</b>
            </div>
          )}
        />
        {modalOpen &&
          <CreateUpdateModal
            modalOpen={modalOpen}
            handleCreate={handleCreate}
            handleUpdate={handleUpdate}
            onClose={() => setModalOpen(false)}
            editData={editData}
          />
        }
      </>
    </>
  )
})

export default Teacher
