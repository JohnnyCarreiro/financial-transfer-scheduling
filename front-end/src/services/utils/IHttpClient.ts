export interface HttpClient {
	get<T>(url: string, params?: Record<string, any>): Promise<T>;
	post<T, P>(url: string, payload: P): Promise<T>;
	put<T, P>(url: string, payload: P): Promise<T>;
	delete(url: string): Promise<void>;
}
