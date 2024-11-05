import {jsonRequest, jsonRequestWithToken} from "@util/request"
import {getApiUrl} from "../../base"
import {toQueryString} from "@util/queryString";

const baseUrl = '/exercise'

export async function getList(token, params) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function getAll(token, params) {
    return jsonRequestWithToken(`${getApiUrl()}${baseUrl}?${toQueryString(params)}`, 'GET', token)
}

export async function getId(id) {
    return jsonRequest(`${getApiUrl()}${baseUrl}/${id}`, 'GET')
}