import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {NextSeo} from 'next-seo'
import {Button, Card, Divider, Form, Input, message, Popconfirm, Table, Tag, Tooltip} from 'antd'
import {DeleteTwoTone, EditTwoTone, PlusOutlined} from '@ant-design/icons'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import numeral from 'numeral'

import {useStore} from '../../../context/mobxStore'
import {checkAuthRole} from '../../../common/util/auth'

const PageHeader = dynamic(() => import('../../../components/elements/pageHeader'))
const CreateUpdateModal = dynamic(() => import('./createUpdate'))

const FormItem = Form.Item

const BookType = observer(() => {
  const {data: session = {}} = useSession()
  const [form] = Form.useForm()
  const bookTypeStore = useStore('bookTypeStore')
  const {data, loading, searchFormValues} = bookTypeStore
  const {list, pagination} = data

  const [modalOpen, setModalOpen] = useState(false)
  const [editData, setEditData] = useState({})

  useEffect(() => {
    if (session?.token) {
      refreshTable()
    }
  }, [session])

  const refreshTable = (params) => {
    bookTypeStore.fetchList(params, session?.token)
  }

  const handleTableChange = (pagination) => {
    const params = {
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize
    }

    bookTypeStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const handleFormReset = () => {
    form.resetFields()
    bookTypeStore.setSearchFormValues({})
    refreshTable()
  }

  const handleSearch = () => {
    bookTypeStore.setSearchFormValues(form.getFieldsValue())
    refreshTable(form.getFieldsValue())
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
    bookTypeStore.create(fields, session?.token).then(response => {
      if (response.result === true && response.data) {
        message.success('Сургалтын төрөл амжилттай бүртгэлээ')
        form.resetFields()
        setModalOpen(false)
        refreshTable()
      } else {
        message.error(`Сургалтын төрөл бүртгэхэд алдаа гарлаа: ${response.message}`)
      }
    }).catch(e => {
      console.log(e)
      message.error(`Сургалтын төрөл бүртгэхэд алдаа гарлаа: ${e.message}`)
    })
  }

  const handleUpdate = (fields, form) => {
    bookTypeStore.update(Object.assign({id: editData.id}, fields), session?.token)
      .then(response => {
        if (response.result === true && response.data) {
          message.success('Сургалтын төрөл амжилттай хадгаллаа')
          form.resetFields()
          setModalOpen(false)
          setEditData({})
          refreshTable()
        } else {
          message.error(`Сургалтын төрөл засварлахад алдаа гарлаа: ${response.message}`)
        }
      })
      .catch(e => {
        console.log(e)
        message.error(`Сургалтын төрөл засварлахад алдаа гарлаа: ${e.message}`)
      })
  }

  const handleDelete = record => {
    bookTypeStore.deleteOne({id: record.id}, session?.token)
      .then(response => {
        if (response.result === true && response.data) {
          message.success('Сургалтын төрөл амжилттай устгалаа')
          handleFormReset()
        } else {
          message.error(`Сургалтын төрөл устгахад алдаа гарлаа: ${response.message}`)
        }
      })
      .catch(e => {
        console.log(e)
        message.error(`Сургалтын төрөл  устгахад алдаа гарлаа: ${e.message}`)
      })
  }

  const columns = [
    {
      title: '№',
      dataIndex: '',
      width: '20px',
      align: 'center',
      render: (_, __, index) => index + 1
    },
    {
      title: 'Нэр',
      dataIndex: 'name',
      width: 200
    },
    {
      title: 'Код',
      dataIndex: 'code',
      width: '150px',
      align: 'center',
      render: text => <Tag color='blue'>{text}</Tag>
    },
    {
      title: 'Идэвхтэй эсэх',
      dataIndex: 'active',
      width: '150px',
      align: 'center',
      render: text => text ? <Tag color='green'>Тийм</Tag> : <Tag color='purple'>Үгүй</Tag>
    },
    {
      title: 'Эрэмбэ',
      dataIndex: 'order',
      width: '150px',
      align: 'center',
    },
    {
      title: 'Үйлдэл',
      width: '200px',
      align: 'center',
      render: (text, record) => (
        <>
          <Tooltip placement='top' title='Засах'>
            <Button
              icon={<EditTwoTone />}
              onClick={() => showModal('UPDATE', record)}
              shape='circle'
            />
          </Tooltip>
          <Divider type='vertical' />
          <Tooltip placement='top' title='Устгах'>
            <Popconfirm title='Устгах уу ?' onConfirm={() => handleDelete(record)}>
              <Button icon={<DeleteTwoTone twoToneColor={'red'}/>} shape='circle' />
            </Popconfirm>
          </Tooltip>
        </>
      )
    }]

  const headerActions = (
    checkAuthRole('ROLE_LOCALE_MANAGE', session?.applicationRoles) ? (
      <Button icon={<PlusOutlined />} type='primary' onClick={() => showModal('CREATE')}>
        Төрөл бүртгэх
      </Button>
    ) : '')

  const renderFilterForm = () => (
    <Form form={form} onFinish={handleSearch} layout='inline'>
      <FormItem
        label='Нэр'
        name='name'
        className='mb10'
      >
        <Input placeholder='Нэр' />
      </FormItem>
      <FormItem
        label='Код'
        name='code'
        className='mb10'
      >
        <Input placeholder='Код' />
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
      <NextSeo title='Сургалтын төрөл - UniCourse' />
      <PageHeader
        routes={[
          {
            title: 'Нүүр',
            link: '/'
          }
        ]}
        title='Сургалтын төрөл'
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
          title={() => (
            <div>
                Нийт төрөл: <b>{data?.pagination?.total && numeral(data.pagination.total).format('0,0') || 0}</b>
            </div>
          )}
          className='custom-table'
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

export default (BookType)
