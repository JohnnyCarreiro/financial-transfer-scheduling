import axios, { AxiosInstance, AxiosRequestConfig } from "axios";
import { HttpClient } from "./IHttpClient";

export class AxiosHttpClient implements HttpClient {
	private client: AxiosInstance;

	constructor(config: AxiosRequestConfig) {
		this.client = axios.create(config);
	}

	async get<T>(url: string, params?: Record<string, any>): Promise<T> {
		const response = await this.client.get<T>(url, { params });
		return response.data;
	}

	async post<T, P>(url: string, payload: P): Promise<T> {
		const response = await this.client.post<T>(url, payload);
		return response.data;
	}

	async put<T, P>(url: string, payload: P): Promise<T> {
		const response = await this.client.put<T>(url, payload);
		return response.data;
	}

	async delete(url: string): Promise<void> {
		await this.client.delete(url);
	}
}
