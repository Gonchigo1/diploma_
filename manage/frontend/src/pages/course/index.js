import {useEffect, useState} from 'react'
import {useSession} from 'next-auth/react'
import {useRouter} from 'next/router'
import {NextSeo} from 'next-seo'
import {observer} from 'mobx-react-lite'
import dynamic from 'next/dynamic'
import {Button, Table,  Image, Tooltip,Input,Card,Form} from 'antd'

import {ArrowRightOutlined, EyeOutlined} from '@ant-design/icons'
import numeral from 'numeral'

import {useStore} from '../../context/mobxStore'

const PageHeader = dynamic(() =>
  import('../../components/elements/pageHeader')
)
const {Item: FormItem} = Form

const OxfordThinkers = observer(() => {
  const router = useRouter()
  const {data: session} = useSession()
  const [form] = Form.useForm()
  const bookStore = useStore('bookStore')
  const {data, loading, searchFormValues} = bookStore
  const {list, pagination} = data

  const [editingKey, _setEditingKey] = useState('')

  const isEditing = (record) => record.key === editingKey
  const PreviewMask = () => <><EyeOutlined /> Томруулах</>

  useEffect(() => {
    if (session?.token) {
      refreshTable()
    }
  }, [session])

  const refreshTable = (params) => {
    bookStore?.fetchList(session?.token, params)
  }

  const handleTableChange = (pagination) => {
    const params = {
      ...searchFormValues,
      currentPage: pagination.current,
      pageSize: pagination.pageSize
    }

    bookStore.setSearchFormValues(params)
    refreshTable(params)
  }

  const handleViewDetails = (id) => {
    router.push(`/course/${id}`)
  }
  const handleFormReset = () => {
    form.resetFields()
    bookStore.setSearchFormValues({})
    refreshTable()
  }
  const handleSearch = () => {
    bookStore.setSearchFormValues(form.getFieldsValue())
    refreshTable(form.getFieldsValue())
  }
  const columns = [
    {
      title: '№',
      width: 50,
      align: 'center',
      render: (_, __, idx) => pagination?.currentPage * pagination?.pageSize + idx + 1
    },
    {
      title: 'Зураг',
      dataIndex: 'image',
      width: 100,
      align: 'center',
      render: (images) => {
        if (images && images.length > 0) {
          const imageUrl = images[0].url
          return (
            <Image
              src={imageUrl}
              alt='bookImage'
              preview={{
                mask: <PreviewMask />
              }}
              width='100px'
            />
          )        }
        return '-'
      },
    },
    {
      title: 'Нэр',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: 'Төрөл',
      dataIndex: 'type',
      width: 200,
    },
    {
      title: 'Үйлдэл',
      dataIndex: 'operation',
      width: 300,
      render: (_, record) => {
        const _editable = isEditing(record)
        return (
          <Tooltip title={'Дэлгэрэнгүй харах'}>
            <Button
              type='link'
              onClick={() => handleViewDetails(record.id)}
              icon={<ArrowRightOutlined/>}
            />
          </Tooltip>
        )
      },
    },
  ]
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
        label='Төрөл'
        name='code'
        className='mb10'
      >
        <Input placeholder='Төрөл' />
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
      <NextSeo title='Сургалт - UniCourse' />
      <PageHeader
        routes={[{title: 'Нүүр', link: '/'}]}
        title='Сургалт'
      />
      <>
        <Card>
          {renderFilterForm()}
        </Card>
        <>
          <Table
            dataSource={list || []}
            loading={loading}
            columns={columns}
            rowKey='id'
            size='middle'
            onChange={handleTableChange}
            style={{marginTop: '20px'}}
            className='custom-table'
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
            title={() => (
              <div>
              Нийт бичлэг: <b>{data?.pagination?.total && numeral(data.pagination.total).format('0,0') || 0}</b>
              </div>
            )}
            onRow={(record) => {
              return {
                onClick: () => handleViewDetails(record.id),
              }
            }}
          />
        </>
      </>
    </>
  )
})

export default OxfordThinkers
