import {jsonRequestWithToken} from '../../util/request'
import {toQueryString} from '../../util/queryString'
import {getApiUrl} from '../../base'

const baseUrl = '/v1/user'

export async function list(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function create(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/create`, 'POST', token, JSON.stringify(params))
}
export async function get(id, token) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/${id}`, 'GET', token)
}
export async function select(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/select?${toQueryString(params)}`, 'GET', token)
}
export async function update(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/update`, 'PUT', token, JSON.stringify(params))
}

export async function deleteOne(params, token) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/delete`, 'DELETE', token, JSON.stringify(params))
}
