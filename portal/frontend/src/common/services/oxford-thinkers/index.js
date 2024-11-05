import {getApiUrl} from '../../base'
import {jsonRequest, jsonRequestWithToken} from '@util/request'
import {toQueryString} from '@util/queryString'

const baseUrl = '/oxfordThinkers'

export async function getList(token, params) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function get(id) {
    return jsonRequest(getApiUrl() + `${baseUrl}/${id}`, 'GET')
}

export async function getUpdate(params) {
    return jsonRequest(`${getApiUrl()}${baseUrl}/update?${toQueryString(params)}`, 'POST')
}

export async function getListBook(token) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}`, 'GET', token)
}

export async function getAll(token) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}`, 'GET', token)
}