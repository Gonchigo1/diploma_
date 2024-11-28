import {
  getApiUrl
} from '../../base'
import {
  jsonRequestWithToken,
  jsonRequest
} from '../../util/request'
import {
  toQueryString
} from '../../util/queryString'

const baseUrl = '/v1/oxfordThinkers'

export async function fetchOne(id) {
  return jsonRequest(`${getApiUrl()}${baseUrl}/${id}`, 'GET')
}

export async function create(token, params) {
  return jsonRequestWithToken(
    `${getApiUrl()}${baseUrl}/create`,
    'POST',
    token,
    JSON.stringify(params)
  )
}

export async function update(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/update`, 'POST', token, JSON.stringify(params))
}
export async function getList(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}
export async function fetchAll(token) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}`, 'GET', token)
}

export const deleteItem = async (token, id) => {
  try {
    const response = await fetch(`${getApiUrl()}${baseUrl}/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })

    if (!response.ok) {
      const errorResponse = await response.json()
      throw new Error(errorResponse.message || 'Failed to delete the item.')
    }

    return response.json()
  } catch (error) {
    console.error('Error in deleteItem:', error)
    throw error
  }

}