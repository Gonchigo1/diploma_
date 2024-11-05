import {getApiUrl} from '../../base'
import {jsonRequest, jsonRequestWithToken} from '@util/request'
import {toQueryString} from "@util/queryString"

const baseUrl = '/book'

export async function getList(token, params) {
  return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function getId(id) {
  return jsonRequest(`${getApiUrl()}${baseUrl}/${id}`, 'GET')
}

