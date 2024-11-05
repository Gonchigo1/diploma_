import {getApiUrl} from '../../base'
import {jsonRequestWithToken,jsonRequest} from '../../util/request'
import {toQueryString} from '../../util/queryString'

const baseUrl = '/v1/book'

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
export async function getList(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function deleteOne(id, token) {
  return jsonRequestWithToken(getApiUrl() + `${baseUrl}/${id}`, 'DELETE', token)
}
