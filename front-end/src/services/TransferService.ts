import { ITransferService } from "./ITransferService";
import { HttpClient } from "./utils/IHttpClient";

export class TransferService<T> implements ITransferService<T> {
	private readonly client: HttpClient;
	private readonly resourceUrl: string;

	constructor(client: HttpClient, resourceUrl: string) {
		this.client = client;
		this.resourceUrl = resourceUrl;
	}

	async listTransfers(): Promise<T[]> {
		return this.client.get<T[]>(this.resourceUrl);
	}

	async getTransferById(id: string): Promise<T> {
		return this.client.get<T>(`${this.resourceUrl}/${id}`);
	}

	async createTransfer(payload: Partial<T>): Promise<T> {
		return this.client.post<T, Partial<T>>(this.resourceUrl, payload);
	}

	async updateTransfer(id: string, payload: Partial<T>): Promise<T> {
		return this.client.put<T, Partial<T>>(`${this.resourceUrl}/${id}`, payload);
	}

	async deleteTransfer(id: string): Promise<void> {
		return this.client.delete(`${this.resourceUrl}/${id}`);
	}
}
