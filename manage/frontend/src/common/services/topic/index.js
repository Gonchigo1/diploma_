import {getApiUrl} from '../../base'
import {jsonRequestWithToken} from '../../util/request'

const baseUrl = '/v1/topic'

export async function fetchOne(token, id) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}/${id}`, 'GET', token)
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
  return jsonRequestWithToken(
		`${getApiUrl()}${baseUrl}/update`,
		'POST',
		token,
		JSON.stringify(params)
  )
}

export async function fetchAll(token) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}`, 'GET', token)
}
